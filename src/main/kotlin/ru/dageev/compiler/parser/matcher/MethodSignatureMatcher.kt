package ru.dageev.compiler.parser.matcher

import ru.dageev.compiler.domain.scope.MethodSignature

/**
 * Created by dageev
 *  on 15-May-16.
 */
class MethodSignatureMatcher {
    fun matches(methodSignature: MethodSignature, other: MethodSignature): Boolean {
        if (methodSignature.name != other.name) {
            return false
        }

        if (methodSignature.parameters.size != other.parameters.size) {
            return false;
        }

        methodSignature.parameters.forEachIndexed { i, parameter ->
            val otherType = other.parameters[i].type
            if (parameter.type != otherType) {
                return false
            }
        }
        return true
    }

}