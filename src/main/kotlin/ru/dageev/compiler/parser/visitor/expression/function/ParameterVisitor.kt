package ru.dageev.compiler.parser.visitor.expression.function

import ru.dageev.compiler.domain.node.expression.Parameter
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ParameterVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Parameter>() {

    override fun visitFormalParameter(ctx: ElaginParser.FormalParameterContext): Parameter {
        val name = ctx.Identifier().text
        val type = getType(ctx.type())
        return Parameter(name, type)
    }

}