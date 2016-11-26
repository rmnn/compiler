package ru.dageev.compiler.bytecodegeneration.method

import org.objectweb.asm.ClassWriter
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block

/**
 * Created by dageev
 * on 11/26/16.
 */
class ConstructorGenerator(val classWriter: ClassWriter) : AbstractMethodGenerator() {

    val statementGenerator = StatementGenerator(classWriter)

    fun generate(constructor: MethodDeclaration.ConstructorDeclaration) {
        val description = getMethodDescriptor(constructor.methodSignature)

        val methodVisitor = classWriter.visitMethod(constructor.methodSignature.accessModifier.opCode, "<init>", description, null, null)
        methodVisitor.visitCode()
        constructor.statement.accept(statementGenerator)
        appendReturnIfNotExists(constructor, constructor.statement as Block, statementGenerator)
        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

}

