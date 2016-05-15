package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.ReadStatement
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ReadStatementVisitor() : ElaginBaseVisitor<ReadStatement>() {
    override fun visitRead(ctx: ElaginParser.ReadContext): ReadStatement {
        val identifier = ctx.Identifier().text
        return ReadStatement(identifier)
    }
}