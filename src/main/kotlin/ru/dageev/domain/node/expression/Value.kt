package ru.dageev.domain.node.expression

import ru.dageev.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Value(val value: String, type: Type) : Expression(type)