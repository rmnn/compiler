package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 * on 10/30/16.
 */
class BlockStatementVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<Block>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitBlock(ctx: ElaginParser.BlockContext): Block {
        val statements = ctx.statement().map { it.accept(StatementVisitor(scope, classesContext)) }
        return Block(scope, statements)
    }
}