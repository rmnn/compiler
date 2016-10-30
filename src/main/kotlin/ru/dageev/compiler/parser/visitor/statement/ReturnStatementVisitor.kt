package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.ReturnStatement
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 * on 10/30/16.
 */
class ReturnStatementVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<ReturnStatement>() {
    override fun visitReturnStatement(ctx: ElaginParser.ReturnStatementContext): ReturnStatement {
        val expression = ctx.expression().accept(expressionVisitor)
        return ReturnStatement(expression)
    }
}