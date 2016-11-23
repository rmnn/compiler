package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.node.expression.Parameter
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 14-May-16.
 */
class MethodSignature(val accessModifier: AccessModifier, val name: String, val parameters: List<Parameter>, val returnType: Type) {
    override fun toString(): String {
        return "${accessModifier.modifierName} $name(${parameters.map { it.type.getTypeName() }.joinToString()}): ${returnType.getTypeName()}"
    }
}