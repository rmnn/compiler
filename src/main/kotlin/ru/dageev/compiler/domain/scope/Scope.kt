package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.domain.node.expression.Argument
import ru.dageev.compiler.parser.CompilationException
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
        if (fields.containsKey(field.name)) {
            throw  CompilationException("Field ${field.name} already exists in class $className")
        }
        fields.put(field.name, field)
    }

    fun addSignature(signature: MethodSignature) {
        if (signatureExists(signature)) {
            throw  CompilationException("Function $signature already exists for $className")
        } else {
            methodSignatures.add(signature)
        }
    }

    fun addLocalVariable(variable: LocalVariable) {
        if (localVariables.containsKey(variable.name) && variable.name != "this") {
            throw  CompilationException("Local variable ${variable.name} already exists in class $className")
        }
        localVariables.put(variable.name, variable)
    }

    fun getMethodCallSignature(name: String, arguments: List<Argument>): MethodSignature {
        val signature = methodSignatures.find { signature -> signatureMatcher.matches(signature, name, arguments) }
        return signature ?: throw CompilationException("Method '$name${arguments.map { it.type.getTypeName() }}' not found for class '$className'")
    }


    fun methodCallSignatureExists(name: String, arguments: List<Argument>) =
            methodSignatures.find { signature -> signatureMatcher.matches(signature, name, arguments) } != null

    fun fieldExists(name: String) = fields.containsKey(name)

    private fun signatureExists(signature: MethodSignature): Boolean {
        return methodSignatures.any {
            signatureMatcher.matches(it, signature)
        }
    }


}