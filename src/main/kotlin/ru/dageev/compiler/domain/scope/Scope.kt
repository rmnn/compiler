package ru.dageev.compiler.domain.scope

import org.apache.commons.collections4.map.LinkedMap
import ru.dageev.compiler.domain.node.expression.Argument
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.matcher.MethodSignatureMatcher
import java.util.*

/**
 * Created by dageev
 *  on 15-May-16.
 */
data class Scope(val className: String, val parentClassName: String?,
                 val constructorSignatures: MutableList<MethodSignature> = mutableListOf(),
                 val methodSignatures: MutableList<MethodSignature> = mutableListOf(),
                 val localVariables: LinkedMap<String, LocalVariable> = LinkedMap<String, LocalVariable>(),
                 val fields: MutableMap<String, Field> = mutableMapOf()) {

    val signatureMatcher = MethodSignatureMatcher()

    fun addField(field: Field) {
        if (fields.containsKey(field.name)) {
            throw  CompilationException("Field '${field.name}' already exists in class '$className'")
        }
        fields.put(field.name, field)
    }

    fun addSignature(signature: MethodSignature) {
        if (signatureExists(signature)) {
            throw  CompilationException("Function signature '$signature' already exists for $className")
        } else {
            methodSignatures.add(signature)
        }
    }


    fun addConstructorSignature(signature: MethodSignature) {
        if (constructorSignatureExists(signature)) {
            throw  CompilationException("Constructor signature '$signature' already exists for $className")
        } else {
            constructorSignatures.add(signature)
        }
    }

    fun addLocalVariable(variable: LocalVariable) {
        if (localVariables.containsKey(variable.name) && variable.name != "this") {
            throw  CompilationException("Local variable '${variable.name}' already exists in class '$className'")
        }
        localVariables.put(variable.name, variable)
    }

    fun getMethodCallSignature(name: String, arguments: List<Argument>): Optional<MethodSignature> {
        return Optional.ofNullable(methodSignatures.find { signature -> signatureMatcher.matches(signature, name, arguments) })
    }


    fun fieldExists(name: String) = fields.containsKey(name)

    fun signatureExists(signature: MethodSignature): Boolean = signatureExists(signature, methodSignatures)

    private fun constructorSignatureExists(signature: MethodSignature): Boolean = signatureExists(signature, constructorSignatures)

    private fun signatureExists(signature: MethodSignature, signatures: List<MethodSignature>): Boolean {
        return signatures.any {
            signatureMatcher.matches(it, signature)
        }
    }

    fun copy(): Scope = Scope(className, parentClassName, ArrayList(constructorSignatures), ArrayList(methodSignatures), LinkedMap(localVariables), HashMap(fields))

}