package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.PrintStatement
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class PrintStatementVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<PrintStatement>() {
    override fun visitPrint(ctx: ElaginParser.PrintContext): PrintStatement {
        val expression = ctx.expression().accept(expressionVisitor)
        return PrintStatement(expression)
    }
}