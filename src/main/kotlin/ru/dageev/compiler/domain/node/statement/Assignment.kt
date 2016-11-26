package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.type.ClassType

/**
 * Created by dageev
 *  on 14-May-16.
 */
class Assignment(val classType: ClassType, val varName: String, expression: Expression) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }
}