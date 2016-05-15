package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.declaration.VariableDeclaration
import ru.dageev.compiler.domain.node.expression.EmptyExpression
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.visitor.statement.StatementVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodVisitor(val scope: Scope) : ElaginBaseVisitor<MethodDeclaration>() {
    override fun visitMethodDeclaration(ctx: ElaginParser.MethodDeclarationContext): MethodDeclaration {
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val signature = ctx.accept(MethodSignatureVisitor(scope))
        val block = ctx.accept(StatementVisitor(scope))
        signature.parameters.forEach { param ->
            scope.addLocalVariable(VariableDeclaration.LocalVariable(param.name, param.type, EmptyExpression(param.type)))
        }
        return MethodDeclaration.ConstructorDeclaration(accessModifier, signature, block)
    }

}