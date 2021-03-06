package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 * on 11/2/16.
 */
class FieldAccess(val identifier: String, type: Type, val variableReference: VariableReference) : Expression(type) {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun accept(generator: ExpressionGenerator) {
        generator.generate(this)
    }
}