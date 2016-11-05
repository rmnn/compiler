package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.FieldAccess
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 * on 11/2/16.
 */
class FieldAccessExpressionVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<FieldAccess>() {
    override fun visitFieldAccessor(ctx: ElaginParser.FieldAccessorContext): FieldAccess {
        val expression = ctx.expression().accept(expressionVisitor)
        return FieldAccess(ctx.Identifier().text, expression.type as ClassType)
    }
}