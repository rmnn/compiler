package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
class WhileStatement(val condition: Expression, val body: Statement) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }
}