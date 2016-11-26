package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 * on 11/26/16.
 */
class EmptyExpression(type: Type) : Expression(type) {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun generate(generator: ExpressionGenerator) {
        generator.generate(this)
    }
}