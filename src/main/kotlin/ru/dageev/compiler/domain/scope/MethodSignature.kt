package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.node.expression.Parameter
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 14-May-16.
 */
data class MethodSignature(val accessModifier: AccessModifier, val tailrec: Boolean, val name: String, val parameters: List<Parameter>, val returnType: Type) {
    override fun toString(): String {
        return "${accessModifier.modifierName} $name(${parameters.map { it.type.getTypeName() }.joinToString()}): ${returnType.getTypeName()}"
    }

    fun getDescriptor(): String {
        val parametersDescriptor = parameters.map { it.type.getDescriptor() }.joinToString(separator = "", prefix = "(", postfix = ")")
        val returnType = returnType.getDescriptor()
        return parametersDescriptor + returnType
    }
}