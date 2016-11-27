package ru.dageev.compiler.bytecodegeneration.method

import org.objectweb.asm.ClassWriter
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block

/**
 * Created by dageev
 * on 11/26/16.
 */
class ConstructorGenerator(val classesContext: ClassesContext, val classWriter: ClassWriter) : AbstractMethodGenerator() {

    fun generate(constructor: MethodDeclaration.ConstructorDeclaration) {
        val descriptor = constructor.methodSignature.getDescriptor()
        val block = constructor.statement as Block

        val methodVisitor = classWriter.visitMethod(constructor.methodSignature.accessModifier.opCode, "<init>", descriptor, null, null)
        methodVisitor.visitCode()

        val generator = StatementGenerator(block.scope, classesContext, methodVisitor)
        constructor.statement.accept(generator)
        appendReturnIfNotExists(constructor, block, generator)
        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

}

