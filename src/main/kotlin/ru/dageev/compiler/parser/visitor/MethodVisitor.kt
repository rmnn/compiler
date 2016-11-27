package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.statement.StatementVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodVisitor(scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<MethodDeclaration>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitMethodDeclaration(ctx: ElaginParser.MethodDeclarationContext): MethodDeclaration {
        scope.addLocalVariable(LocalVariable("this", ClassType(scope.className)))
        val signature = ctx.accept(MethodSignatureVisitor(scope, typeProvider, classesContext))
        signature.parameters.forEach { param ->
            scope.addLocalVariable(LocalVariable(param.name, param.type))
        }

        val block = ctx.accept(StatementVisitor(scope, typeProvider, classesContext))
        return MethodDeclaration(signature, block)
    }

}