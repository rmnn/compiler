package ru.dageev.compiler.bytecodegeneration.method

import jdk.internal.org.objectweb.asm.ClassWriter
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.expression.Call
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType

/**
 * Created by dageev
 * on 11/26/16.
 */
class ConstructorGenerator(val classesContext: ClassesContext, val classWriter: ClassWriter, val parentClass: String) : AbstractMethodGenerator() {

    fun generate(constructor: MethodDeclaration.ConstructorDeclaration) {
        val descriptor = constructor.methodSignature.getDescriptor()
        val block = constructor.statement as Block

        val methodVisitor = classWriter.visitMethod(constructor.methodSignature.accessModifier.opCode, "<init>", descriptor, null, null)
        methodVisitor.visitCode()

        val generator = StatementGenerator(block.scope, classesContext, methodVisitor)
        if (block.statements.none { it is Call.SuperCall }) {
            val methodSignature = MethodSignature(AccessModifier.PUBLIC, false, "super", emptyList(), PrimitiveType.VOID)
            Call.SuperCall(methodSignature, emptyList(), ClassType(parentClass)).accept(generator)
        }
        constructor.statement.accept(generator)
        appendReturnIfNotExists(constructor, block, generator)
        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }

}

