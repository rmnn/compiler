package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.ReadStatement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.parser.helper.getField


/**
 * Created by dageev
 * on 11/27/16.
 */
class ReadStatementGenerator(val scope: Scope, val classesContext: ClassesContext, val methodVisitor: MethodVisitor) {

    fun generate(readStatement: ReadStatement) {
        createScanner()
        printQueryForInput()

        when (getReadFieldType(readStatement.varName)) {
            PrimitiveType.BOOLEAN -> this.methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextBoolean", "()Z", false)
            PrimitiveType.INT -> this.methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", "()I", false)
        }

        visitStoreVariable(readStatement.varName)

    }

    private fun createScanner() {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/Scanner");
        methodVisitor.visitInsn(Opcodes.DUP)
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;")
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false)
    }

    private fun printQueryForInput() {
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        methodVisitor.visitLdcInsn("Enter a value:")
        val descriptor = "(" + ClassType(String::class.java.name).getDescriptor() + ")V"
        val fieldDescriptor = ClassType("java.io.PrintStream").getDescriptor()
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, fieldDescriptor, "println", descriptor, false)
    }

    private fun getReadFieldType(varName: String): Type {
        return if (scope.localVariables.containsKey(varName)) scope.localVariables[varName]!!.type
        else getField(classesContext, scope, ClassType(scope.className), varName).get().type
    }


    fun visitStoreVariable(varName: String) {
        val location = scope.localVariables.indexOf(varName)

        if (location < 0) {
            val field = getField(classesContext, scope, ClassType(scope.className), varName).get()
            this.methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            this.methodVisitor.visitInsn(Opcodes.SWAP)

            this.methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.ownerType.getInternalName(), field.name, field.type.getDescriptor())
        } else {
            methodVisitor.visitVarInsn(Opcodes.ISTORE, location)
        }
    }
}