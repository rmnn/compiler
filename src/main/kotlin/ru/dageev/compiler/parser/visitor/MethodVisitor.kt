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
import ru.dageev.compiler.parser.helper.TailRecOptimizer
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.statement.StatementVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodVisitor(scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<MethodDeclaration>() {
    val scope: Scope
    val tailRecOptimizer: TailRecOptimizer


    init {
        this.scope = scope.copy()
        this.tailRecOptimizer = TailRecOptimizer()
    }

    override fun visitMethodDeclaration(ctx: ElaginParser.MethodDeclarationContext): MethodDeclaration {
        if (scope.className != "ElaginProgram") {
            scope.addLocalVariable(LocalVariable("this", ClassType(scope.className)))
        }
        val signature = ctx.accept(MethodSignatureVisitor(scope, typeProvider, classesContext))
        signature.parameters.forEach { param ->
            scope.addLocalVariable(LocalVariable(param.name, param.type))
        }

        if (signature.tailrec) {
            scope.addLocalVariable(LocalVariable("elaginAccum", signature.returnType))
        }

        val block = ctx.accept(StatementVisitor(scope, typeProvider, classesContext)) as Block

        if (signature.returnType != PrimitiveType.VOID) {
            if (!containsReturnStatement(block)) {
                throw CompilationException("Method $signature should have return statement at the end")
            }
        }
        val methodDeclaration = MethodDeclaration(signature, block)
        return if (signature.tailrec) tailRecOptimizer.generate(methodDeclaration)
        else methodDeclaration

    }


    private fun lastStatementContainsReturn(statement: Statement): Boolean {
        return when (statement) {
            is ReturnStatement -> true
            is Block -> containsReturnStatement(statement)
            is IfStatement -> lastStatementContainsReturn(statement.trueStatement) && statement.elseStatement.map { lastStatementContainsReturn(it) }.orElse(true)
            is WhileStatement -> lastStatementContainsReturn(statement.body)
            else -> false
        }
    }

    private fun containsReturnStatement(block: Block) = !block.statements.isEmpty() && lastStatementContainsReturn(block.statements.last())
}
