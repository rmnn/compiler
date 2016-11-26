package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Block(val scope: Scope, val statements: List<Statement>) : Statement {

    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }

    override fun toString(): String {
        return "Block(statements=$statements)"
    }
}