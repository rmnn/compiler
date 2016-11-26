package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 * on 10/30/16.
 */
class VariableDeclaration(val name: String, val type: Type, val expression: Expression) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun toString() = "$name: $type = $expression"
}