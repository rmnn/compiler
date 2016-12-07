package ru.dageev.compiler.bytecodegeneration.expression

import jdk.internal.org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.node.expression.Parameter
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/27/16.
 */
class ParameterExpressionGenerator(val scope: Scope, val methodVisitor: MethodVisitor) {

    fun generate(parameter: Parameter) {
        val index = scope.localVariables.indexOf(parameter.name)
        methodVisitor.visitVarInsn(parameter.type.getLoadVariableOpcode(), index)
    }
}