package ru.dageev.compiler.bytecodegeneration

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.scope.Field

/**
 * Created by dageev
 * on 11/26/16.
 */
class FieldGenerator(val classWriter: ClassWriter) {

    fun generate(field: Field) {
        val fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, field.type.getDescriptor(), null, null)
        fieldVisitor.visitEnd()
    }
}