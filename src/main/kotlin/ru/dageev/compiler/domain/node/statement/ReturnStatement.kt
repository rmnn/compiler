package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.domain.node.expression.Expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ReturnStatement(val expression: Expression) : Statement {
    override fun toString(): String {
        return "ReturnStatement(expression=$expression)"
    }
}