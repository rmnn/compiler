package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.Argument
import ru.dageev.compiler.domain.node.expression.Call
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.matcher.MethodSignatureMatcher

/**
 * Created by dageev
 * on 10/30/16.
 */
class MethodCallExpressionVisitor(scope: Scope, val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Call>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitMethodCall(ctx: ElaginParser.MethodCallContext): Call {
        val functionName = ctx.identifier().text
        if (functionName == scope.className) {
            throw IllegalArgumentException(functionName)
        }

        val arguments = getArgumentsForCall(ctx.expressionList())
        val (owner, scope) = getOwnerAndScope(ctx)

        val methodCallSignature = getMethodSignature(scope.className, scope, functionName, arguments)
        return Call.MethodCall(methodCallSignature, functionName, arguments, owner)
    }

    override fun visitSuperCall(ctx: ElaginParser.SuperCallContext): Call {
        val arguments = getArgumentsForCall(ctx.expressionList())
        return Call.SuperCall(arguments, ClassType(scope.className))
    }


    fun getMethodSignature(childClass: String, scope: Scope, functionName: String, arguments: List<Argument>): MethodSignature {
        val methodCallSignature = scope.getMethodCallSignature(functionName, arguments)
        return if (methodCallSignature.isPresent) {
            if (scope.className != childClass && methodCallSignature.get().accessModifier == AccessModifier.PRIVATE) {
                throw  CompilationException("Unable to call parent '${scope.className}' class private method '$functionName'")
            }
            methodCallSignature.get()

        } else {
            if (scope.parentClassName != null) {
                val parentScope = classesContext.getClassScope(scope.parentClassName)
                getMethodSignature(childClass, parentScope, functionName, arguments)
            } else {
                throw CompilationException("Method '$functionName${arguments.map { it.type.getTypeName() }}' not found for class '$childClass'")
            }
        }
    }

    override fun visitConstructorCall(ctx: ElaginParser.ConstructorCallContext): Call {
        val className = ctx.identifier().text
        val arguments = getArgumentsForCall(ctx.expressionList())

        val constructors = if (className == scope.className) scope.constructorSignatures
        else {
            val classDecl = classesContext.classes[className]
            classDecl ?: throw CompilationException("Unable to create object of class '$className' due it doesn't exist ")
            classesContext.getClassScope(className).constructorSignatures
        }
        val constructorExists = constructors
                .filter { it.accessModifier == AccessModifier.PUBLIC || scope.className == className }
                .any { constructor ->
                    MethodSignatureMatcher().matches(constructor, className, arguments)
                }
        if (!constructorExists) {
            throw CompilationException("Could not find matching constructor with arguments $arguments for $className")
        }

        return Call.ConstructorCall(className, arguments)
    }

    private fun getOwnerAndScope(ctx: ElaginParser.MethodCallContext): Pair<Expression, Scope> {
        return if (ctx.classRef != null) {
            val classRefExpression = ctx.classRef.accept(expressionVisitor)
            if (classRefExpression.type is ClassType) {
                classRefExpression to classesContext.getClassScope(classRefExpression.type.getTypeName())
            } else {
                throw CompilationException("Unable to call methods for primitive types for")
            }
        } else {
            val localVariable = LocalVariable("this", ClassType(scope.className))
            VariableReference.LocalVariableReference(localVariable) to scope
        }
    }

    private fun getArgumentsForCall(expressionList: ElaginParser.ExpressionListContext?): List<Argument> {
        if (expressionList == null) {
            return emptyList()
        }

        return expressionList.expression().map { Argument(it.accept(expressionVisitor)) }
    }
}