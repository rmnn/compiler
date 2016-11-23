package ru.dageev.compiler.domain.node.expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Argument(val expression: Expression) : Expression(expression.type) {
    override fun toString() = expression.type.getTypeName()
}