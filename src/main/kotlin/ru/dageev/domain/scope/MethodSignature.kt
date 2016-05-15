package ru.dageev.domain.scope

import ru.dageev.domain.node.expression.Parameter
import ru.dageev.domain.type.Type

/**
 * Created by dageev
 *  on 14-May-16.
 */
class MethodSignature(val name: String, val parameters: List<Parameter>, val returnType: Type)