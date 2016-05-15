package ru.dageev.domain.node.statement

import java.util.*

/**
 * Created by dageev
 *  on 15-May-16.
 */
class IfStatement(val condition: Exception, val trueStatement: Statement, elseStatement: Optional<Statement>) : Statement