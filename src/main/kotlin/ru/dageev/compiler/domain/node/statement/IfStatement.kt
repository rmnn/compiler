package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression
import java.util.*

/**
 * Created by dageev
 *  on 15-May-16.
 */
class IfStatement(val condition: Expression, val trueStatement: Statement, elseStatement: Optional<Statement>) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }
}