package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 * on 10/30/16.
 */
class AssignmentStatementVisitor(val scope: Scope, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Assignment>() {


    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Assignment {
        val name = ctx.Identifier().text
        val expression = ctx.assignmentExpr.accept(expressionVisitor)

        return if (ctx.classExpr != null) {
            val expr = ctx.classExpr.accept(expressionVisitor)
            return if (expr.type is ClassType) {
                Assignment(expr.type, name, expression)
            } else {
                throw RuntimeException("Failed to ")
            }
        } else {
            Assignment(ClassType(scope.className), name, expression)
        }
    }
}