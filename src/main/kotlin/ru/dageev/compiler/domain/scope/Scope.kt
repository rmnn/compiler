package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.domain.node.expression.Argument
import ru.dageev.compiler.parser.matcher.MethodSignatureMatcher

/**
 * Created by dageev
 *  on 15-May-16.
 */
data class Scope(val className: String, val parentClassName: String?,
                 val methodSignatures: MutableList<MethodSignature> = mutableListOf(),
                 val localVariables: MutableMap<String, LocalVariable> = mutableMapOf(),
                 val fields: MutableMap<String, Field> = mutableMapOf()) {

    val signatureMatcher = MethodSignatureMatcher()

    fun addField(field: Field) {
        fields.put(field.name, field)
    }

    fun addSignature(signature: MethodSignature) {
        if (signatureExists(signature)) {
            // TODO ERROR HANDLER
        } else {
            methodSignatures.add(signature)
        }
    }

    fun addLocalVariable(variable: LocalVariable) {
        localVariables.put(variable.name, variable)
    }

    fun getMethodCallSignature(name: String, arguments: List<Argument>): MethodSignature {
        val signature = methodSignatures.find { signature -> signatureMatcher.matches(signature, name, arguments) }
        return signature ?: throw RuntimeException("Method '$name${arguments.map { it.type.getTypeName() }}' not found for class '$className'")
    }


    fun methodCallSignatureExists(name: String, arguments: List<Argument>): Boolean =
            methodSignatures.find { signature -> signatureMatcher.matches(signature, name, arguments) } != null

    private fun signatureExists(signature: MethodSignature): Boolean {
        return methodSignatures.any {
            signatureMatcher.matches(it, signature)
        }
    }


}