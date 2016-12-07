package ru.dageev.compiler.bytecodegeneration

import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.method.ConstructorGenerator
import ru.dageev.compiler.bytecodegeneration.method.MethodGenerator
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.ClassDeclaration

/**
 * Created by dageev
 * on 11/26/16.
 */
class ClassGenerator(val classesContext: ClassesContext) {

    fun generate(classDeclaration: ClassDeclaration, forMainClass: Boolean = false): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, classDeclaration.name, null,
                classDeclaration.parentClassDeclaration.map { it.name }.orElseGet { "java/lang/Object" }, null)

        val fieldGenerator = FieldGenerator(classesContext.toScope(classDeclaration), classesContext, classWriter)
        classDeclaration.fields.forEach { it.accept(fieldGenerator) }

        val constructorGenerator = ConstructorGenerator(classesContext, classWriter, classDeclaration.parentClassDeclaration.map { it.name }.orElse("java.lang.Object"))
        classDeclaration.constructors.forEach { it.accept(constructorGenerator) }

        val methodGenerator = MethodGenerator(classesContext, classWriter, forMainClass)
        classDeclaration.methods.forEach { it.accept(methodGenerator) }
        classWriter.visitEnd()
        return classWriter.toByteArray()
    }
}

