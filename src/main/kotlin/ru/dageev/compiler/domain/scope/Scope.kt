package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.parser.matcher.MethodSignatureMatcher

/**
 * Created by dageev
 *  on 15-May-16.
 */
data class Scope(val className: String,
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

    private fun signatureExists(signature: MethodSignature): Boolean {
        return methodSignatures.any {
            signatureMatcher.matches(it, signature)
        }
    }

}