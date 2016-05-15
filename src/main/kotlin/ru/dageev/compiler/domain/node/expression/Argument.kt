package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Argument(parameterName: String, expression: Expression, type: Type) : Expression(type)