package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 * on 10/30/16.
 */
class AssignmentStatementVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Assignment>() {


    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Assignment {
        val name = ctx.Identifier().text
        val expression = ctx.expression().accept(expressionVisitor)
        return Assignment(name, expression)
    }
}