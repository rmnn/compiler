package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getTypeFromValue

/**
 * Created by dageev
 * on 10/30/16.
 */
class ValueExpressionVisitor() : ElaginBaseVisitor<Value>() {

    override fun visitValue(ctx: ElaginParser.ValueContext): Value {
        val value = ctx.text
        val type = getTypeFromValue(ctx)
        return Value(value, type)
    }
}