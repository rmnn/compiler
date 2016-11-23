package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Block(scope: Scope, val statements: List<Statement>) : Statement {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun toString(): String {
        return "Block(statements=$statements)"
    }
}