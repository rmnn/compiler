package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.type.ClassType
import java.util.*

/**
 * Created by dageev
 *  on 14-May-16.
 */
class Assignment(val classType: Optional<ClassType>, val varName: String, val expression: Expression) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun toString(): String {
        return "Assignment(classType=$classType, varName='$varName', expression=$expression)"
    }
}