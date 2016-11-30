package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.*
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
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

        val block = ctx.accept(StatementVisitor(scope, typeProvider, classesContext)) as Block

        if (signature.returnType != PrimitiveType.VOID) {
            if (!containsReturnStatement(block)) {
                throw CompilationException("Method $signature should have return statement at the end")
            }
        }
        return MethodDeclaration(signature, block)
    }

    private fun containsReturnStatement(block: Block) = !block.statements.isEmpty() && lastStatementContainsReturn(block.statements.last())


    private fun lastStatementContainsReturn(statement: Statement): Boolean {
        if (statement is ReturnStatement) {
            return true
        }

        if (statement is IfStatement) {
            return containsReturnStatement(statement.trueStatement as Block) && statement.elseStatement.isPresent && containsReturnStatement(statement.elseStatement.get() as Block)
        }

        if (statement is WhileStatement) {
            return (statement.body as Block).statements.any { it is ReturnStatement }
        }
        return false
    }
}