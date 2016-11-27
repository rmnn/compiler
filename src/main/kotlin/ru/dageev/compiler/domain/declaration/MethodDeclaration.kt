package ru.dageev.compiler.domain.declaration

import ru.dageev.compiler.bytecodegeneration.method.ConstructorGenerator
import ru.dageev.compiler.bytecodegeneration.method.MethodGenerator
import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.scope.MethodSignature

/**
 * Created by dageev
 *  on 15-May-16.
 */
open class MethodDeclaration(val methodSignature: MethodSignature, val statement: Statement) {
    class ConstructorDeclaration(methodSignature: MethodSignature, statement: Statement) :
            MethodDeclaration(methodSignature, statement) {
        fun accept(constructorGenerator: ConstructorGenerator) {
            constructorGenerator.generate(this)
        }
    }

    fun accept(methodGenerator: MethodGenerator) {
        methodGenerator.generate(this)
    }

    override fun toString(): String {
        return "MethodDeclaration(methodSignature=$methodSignature, statement=$statement)"
    }
}