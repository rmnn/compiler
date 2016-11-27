package ru.dageev.compiler.bytecodegeneration.method

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.parser.helper.getMainMethodSignature
import ru.dageev.compiler.parser.matcher.MethodSignatureMatcher

/**
 * Created by dageev
 * on 11/26/16.
 */
class MethodGenerator(val classWriter: ClassWriter) : AbstractMethodGenerator() {


    fun generate(method: MethodDeclaration) {
        val block = method.statement as Block
        val (access, descriptor) = if (isMainMethod(method.methodSignature)) {
            Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC to getMainMethodDescriptor()
        } else {
            method.accessModifier.opCode to getMethodDescriptor(method.methodSignature)
        }

        val methodVisitor = classWriter.visitMethod(access, method.methodSignature.name, descriptor, null, null)
        methodVisitor.visitCode()
        val statementGenerator = StatementGenerator(methodVisitor, block.scope)
        method.statement.accept(statementGenerator)
        appendReturnIfNotExists(method, block, statementGenerator)
        methodVisitor.visitMaxs(-1, -1)
        methodVisitor.visitEnd()
    }


    fun getMainMethodDescriptor(): String {
        val parametersDescriptor = "([Ljava/lang/String;)"
        return parametersDescriptor + PrimitiveType.VOID.getDescriptor()


    }

    fun isMainMethod(methodSignature: MethodSignature) = MethodSignatureMatcher().matches(methodSignature, getMainMethodSignature())

}