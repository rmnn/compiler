package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.TypeProvider

/**
 * Created by dageev
 * on 10/30/16.
 */
class BlockStatementVisitor(scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<Block>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitBlock(ctx: ElaginParser.BlockContext): Block {
        val statementVisitor = StatementVisitor(scope, typeProvider, classesContext)
        val statements = ctx.statement().map { it.accept(statementVisitor) }
        return Block(scope, statements)
    }
}