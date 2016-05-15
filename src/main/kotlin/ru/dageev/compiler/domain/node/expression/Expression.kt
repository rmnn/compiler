package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
open class Expression(val type: Type) : Statement