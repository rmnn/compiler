package ru.dageev.compiler.parser.matcher

import ru.dageev.compiler.domain.node.expression.Argument
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodSignatureMatcher {
    fun matches(methodSignature: MethodSignature, other: MethodSignature): Boolean {
        return matchesByType(methodSignature, other.name, other.parameters.map { it.type })
    }

    fun matches(methodSignature: MethodSignature, name: String, parameters: List<Argument>): Boolean {
        return matchesByType(methodSignature, name, parameters.map { it.type })

    }

    private fun matchesByType(methodSignature: MethodSignature, name: String, parametersType: List<Type>): Boolean {

        if (methodSignature.name != name) {
            return false
        }

        if (methodSignature.parameters.size != parametersType.size) {
            return false;
        }

        methodSignature.parameters.forEachIndexed { i, parameter ->
            val otherType = parametersType[i]
            if (parameter.type != otherType) {
                return false
            }
        }
        return true
    }

}