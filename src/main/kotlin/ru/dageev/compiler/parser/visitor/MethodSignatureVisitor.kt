package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor
import ru.dageev.compiler.parser.visitor.expression.function.ParameterListVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodSignatureVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<MethodSignature>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitMethodDeclaration(ctx: ElaginParser.MethodDeclarationContext): MethodSignature {
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val functionName = ctx.Identifier().text
        val returnType = getType(classesContext, ctx.type())
        val params = ctx.formalParameters().accept(ParameterListVisitor(classesContext, ExpressionVisitor(scope, classesContext)))

        return MethodSignature(accessModifier, functionName, params, returnType)
    }

    override fun visitConstructorDeclaration(ctx: ElaginParser.ConstructorDeclarationContext): MethodSignature {
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val functionName = scope.className
        val returnType = ClassType(scope.className)
        val params = ctx.formalParameters().accept(ParameterListVisitor(classesContext, ExpressionVisitor(scope, classesContext)))

        return MethodSignature(accessModifier, functionName, params, returnType)
    }
}