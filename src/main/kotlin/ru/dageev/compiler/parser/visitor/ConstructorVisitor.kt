package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.declaration.MethodDeclaration.ConstructorDeclaration
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
class ConstructorVisitor(val scope: Scope) : ElaginBaseVisitor<ConstructorDeclaration>() {
    override fun visitConstructorDeclaration(ctx: ElaginParser.ConstructorDeclarationContext): ConstructorDeclaration {
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val signature = ctx.accept(MethodSignatureVisitor(scope))
        signature.parameters.forEach { param ->
            scope.addLocalVariable(VariableDeclaration.LocalVariable(param.name, param.type, EmptyExpression(param.type)))
        }
        val block = ctx.accept(StatementVisitor(scope))
        return ConstructorDeclaration(accessModifier, signature, block)
    }

}