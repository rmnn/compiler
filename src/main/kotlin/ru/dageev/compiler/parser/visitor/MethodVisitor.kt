package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.visitor.statement.StatementVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<MethodDeclaration>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitMethodDeclaration(ctx: ElaginParser.MethodDeclarationContext): MethodDeclaration {
        scope.addLocalVariable(LocalVariable("this", ClassType(scope.className)))

        val accessModifier = getAccessModifier(ctx.accessModifier())
        val signature = ctx.accept(MethodSignatureVisitor(scope, classesContext))
        val block = ctx.accept(StatementVisitor(scope, classesContext))

        signature.parameters.forEach { param ->
            scope.addLocalVariable(LocalVariable(param.name, param.type))
        }

        return MethodDeclaration.ConstructorDeclaration(accessModifier, signature, block)
    }

}