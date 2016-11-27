package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.expression.FieldAccess

/**
 * Created by dageev
 * on 11/27/16.
 */
class FieldAccessGenerator(val methodVisitor: MethodVisitor) {
    fun generate(fieldAccess: FieldAccess) {
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        methodVisitor.visitFieldInsn(Opcodes.GETFIELD, fieldAccess.ownerType.getInternalName(), fieldAccess.identifier, fieldAccess.type.getDescriptor())
    }
}