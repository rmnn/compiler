package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Argument(val expression: Expression) : Expression(expression.type) {
    override fun accept(generator: StatementGenerator) {
        expression.accept(generator)
    }

    override fun accept(generator: ExpressionGenerator) {
        expression.accept(generator)
    }

    override fun toString() = expression.type.getTypeName()
}