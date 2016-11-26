package ru.dageev.compiler.bytecodegeneration

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.method.ConstructorGenerator
import ru.dageev.compiler.bytecodegeneration.method.MethodGenerator
import ru.dageev.compiler.domain.declaration.ClassDeclaration

/**
 * Created by dageev
 * on 11/26/16.
 */
class ClassGenerator() {

    fun generate(classDeclaration: ClassDeclaration): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, classDeclaration.name, null, "java/lang/Object", null)

        val fieldGenerator = FieldGenerator(classWriter)
        classDeclaration.fields.forEach { it.accept(fieldGenerator) }

        val constructorGenerator = ConstructorGenerator(classWriter)
        classDeclaration.constructors.forEach { it.accept(constructorGenerator) }

        val methodGenerator = MethodGenerator(classWriter)
        classDeclaration.methods.forEach { it.accept(methodGenerator) }
        classWriter.visitEnd()
        return classWriter.toByteArray()
    }
}

