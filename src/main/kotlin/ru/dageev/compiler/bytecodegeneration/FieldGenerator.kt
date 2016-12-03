package ru.dageev.compiler.bytecodegeneration

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.parser.helper.getField

/**
 * Created by dageev
 * on 11/26/16.
 */
class FieldGenerator(val scope: Scope, val classesContext: ClassesContext, val classWriter: ClassWriter) {

    fun generate(field: Field) {
        val updatedField = getField(classesContext, scope, field.type, field.name).get()
        val fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, updatedField.name, updatedField.type.getDescriptor(), null, null)
        fieldVisitor.visitEnd()
    }
}