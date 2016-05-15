package ru.dageev.domain.node.statement

import ru.dageev.domain.node.expression.Expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
class WhileStatement(val condition: Expression, val body: Statement) : Statement