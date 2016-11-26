package ru.dageev.compiler.domain.declaration

import ru.dageev.compiler.bytecodegeneration.method.ConstructorGenerator
import ru.dageev.compiler.bytecodegeneration.method.MethodGenerator
import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.scope.MethodSignature

/**
 * Created by dageev
 *  on 15-May-16.
 */
open class MethodDeclaration(val accessModifier: AccessModifier = AccessModifier.PUBLIC, val methodSignature: MethodSignature, val statement: Statement) {
    class ConstructorDeclaration(accessModifier: AccessModifier, methodSignature: MethodSignature, statement: Statement) :
            MethodDeclaration(accessModifier, methodSignature, statement) {
        fun accept(constructorGenerator: ConstructorGenerator) {
            constructorGenerator.generate(this)
        }
    }

    fun accept(methodGenerator: MethodGenerator) {
        methodGenerator.generate(this)
    }

    override fun toString(): String {
        return "MethodDeclaration(accessModifier=$accessModifier, methodSignature=$methodSignature, statement=$statement)"
    }
}