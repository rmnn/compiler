package ru.dageev.compiler.parser.visitor.expression

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
        val functionName = ctx.Identifier().text
        if (functionName == scope.className) {
            throw IllegalArgumentException(functionName)
        }

        val arguments = getArgumentsForCall(ctx.expressionList())
        val (owner, scope) = getOwnerAndScope(ctx)

        val methodCallSignature = getMethodSignature(scope.className, scope, functionName, arguments)
        return Call.MethodCall(methodCallSignature, functionName, arguments, owner)
    }


    fun getMethodSignature(childClass: String, scope: Scope, functionName: String, arguments: List<Argument>): MethodSignature {
        return if (scope.methodCallSignatureExists(functionName, arguments)) {
            scope.getMethodCallSignature(functionName, arguments)
        } else {
            if (scope.parentClassName != null) {
                val parentScope = classesContext.getClassScope(scope.parentClassName)
                getMethodSignature(childClass, parentScope, functionName, arguments)
            } else {
                throw RuntimeException("Method   $functionName${arguments.map { it.type.getTypeName() }}' not found for class ' $childClass'")
            }
        }
    }

    override fun visitConstructorCall(ctx: ElaginParser.ConstructorCallContext): Call {
        val className = ctx.Identifier().text
        val arguments = getArgumentsForCall(ctx.expressionList())
        return Call.ConstructorCall(className, arguments)
    }

    private fun getOwnerAndScope(ctx: ElaginParser.MethodCallContext): Pair<Expression, Scope> {
        return if (ctx.classRef != null) {
            val classRefExpression = ctx.classRef.accept(expressionVisitor)
            // todo error handling should be class type
            classRefExpression to classesContext.getClassScope(classRefExpression.type.getTypeName())
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