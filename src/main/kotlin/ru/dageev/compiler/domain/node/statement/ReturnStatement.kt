package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ReturnStatement(val expression: Expression) : Statement {

    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun toString(): String {
        return "ReturnStatement(expression=$expression)"
    }
}