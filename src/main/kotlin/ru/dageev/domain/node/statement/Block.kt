package ru.dageev.domain.node.statement

import ru.dageev.domain.scope.Scope

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Block(val statements: List<Statement>, val scope: Scope) : Statement