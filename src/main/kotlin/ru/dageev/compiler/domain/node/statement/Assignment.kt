package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.domain.node.expression.Expression

/**
 * Created by dageev
 *  on 14-May-16.
 */
class Assignment(val varName: String, expression: Expression) : Statement