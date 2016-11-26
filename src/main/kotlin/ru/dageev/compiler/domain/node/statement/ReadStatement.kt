package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ReadStatement(val varName: String) : Statement {
    override fun accept(generator: StatementGenerator) {
        generator.generate(this)
    }
}