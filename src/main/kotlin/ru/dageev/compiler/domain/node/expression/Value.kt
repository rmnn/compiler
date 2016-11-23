package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Value(val value: String, type: Type) : Expression(type) {
    override fun toString(): String {
        return "Value(value='$value', type=$type)"
    }

}