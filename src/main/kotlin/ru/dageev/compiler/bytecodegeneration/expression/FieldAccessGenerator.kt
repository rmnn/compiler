package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.expression.FieldAccess
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/27/16.
 */
class FieldAccessGenerator(val scope: Scope, val methodVisitor: MethodVisitor) {
    fun generate(fieldAccess: FieldAccess) {
        if (fieldAccess.variableReference is VariableReference.LocalVariableReference) {
            val index = scope.localVariables.indexOf(fieldAccess.variableReference.localVariable.name)
            methodVisitor.visitVarInsn(Opcodes.ALOAD, index)
        } else {
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        }

        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, fieldAccess.variableReference.type.getInternalName(), fieldAccess.identifier, fieldAccess.type.getDescriptor())
    }
}