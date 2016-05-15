package ru.dageev.domain.node.expression

import ru.dageev.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Parameter(val name: String, type: Type) : Expression(type)