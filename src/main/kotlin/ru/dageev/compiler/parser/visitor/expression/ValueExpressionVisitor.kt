package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 * on 10/30/16.
 */
class ValueExpressionVisitor() : ElaginBaseVisitor<Value>() {

    override fun visitBooleanValue(ctx: ElaginParser.BooleanValueContext): Value {
        val value = ctx.text
        return Value(value, PrimitiveType.BOOLEAN)
    }

    override fun visitIntegerValue(ctx: ElaginParser.IntegerValueContext): Value {
        val value = ctx.text
        return Value(value, PrimitiveType.INT)
    }
}