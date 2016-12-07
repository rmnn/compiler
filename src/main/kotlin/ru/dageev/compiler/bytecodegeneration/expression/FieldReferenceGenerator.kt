package ru.dageev.compiler.bytecodegeneration.expression

import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/27/16.
 */
class FieldReferenceGenerator(val scope: Scope, val methodVisitor: MethodVisitor) {

    fun generate(localVariableReference: VariableReference.LocalVariableReference) {
        val index = scope.localVariables.indexOf(localVariableReference.localVariable.name)
        methodVisitor.visitVarInsn(localVariableReference.localVariable.type.getLoadVariableOpcode(), index)
    }

    fun generate(fieldReference: VariableReference.FieldReference) {
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, fieldReference.field.ownerType.getInternalName(), fieldReference.field.name, fieldReference.field.type.getDescriptor())
    }
}