package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.declaration.MethodDeclaration.ConstructorDeclaration
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.visitor.statement.StatementVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ConstructorVisitor(scope: Scope) : ElaginBaseVisitor<ConstructorDeclaration>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitConstructorDeclaration(ctx: ElaginParser.ConstructorDeclarationContext): ConstructorDeclaration {
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val signature = ctx.accept(MethodSignatureVisitor(scope))
        signature.parameters.forEach { param ->
            scope.addLocalVariable(LocalVariable(param.name, param.type))
        }
        val block = ctx.accept(StatementVisitor(scope))
        return ConstructorDeclaration(accessModifier, signature, block)
    }

}