package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
data class Parameter(val name: String, val type: Type)