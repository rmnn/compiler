package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.statement.ReadStatement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.parser.CompilationException


/**
 * Created by dageev
 * on 11/27/16.
 */
class ReadStatementGenerator(val scope: Scope, val methodVisitor: MethodVisitor) {

    fun generate(readStatement: ReadStatement) {
        this.methodVisitor.visitTypeInsn(Opcodes.NEW, "java/util/Scanner")
        this.methodVisitor.visitInsn(Opcodes.DUP)

        this.methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "in", "Ljava/io/InputStream;")
        this.methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/util/Scanner", "<init>", "(Ljava/io/InputStream;)V", false)

        // Print query
        this.methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        this.methodVisitor.visitLdcInsn(readStatement.varName)
        val type = getReadFieldType(readStatement.varName)
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", type.getDescriptor(), false)


        when (type) {
            PrimitiveType.BOOLEAN -> methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextBoolean", type.getDescriptor(), false)
            PrimitiveType.INT -> methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/util/Scanner", "nextInt", type.getDescriptor(), false);
            else -> throw CompilationException("Unsupported type for read $type")
        }

        visitStoreVariable(readStatement.varName);

    }

    private fun getReadFieldType(varName: String): Type {
        return if (scope.localVariables.containsKey(varName)) scope.localVariables[varName]!!.type
        else scope.fields[varName]!!.type
    }


    fun visitStoreVariable(varName: String) {
        val location = scope.localVariables.indexOf(varName)

        if (location < 0) {
            val field = scope.fields[varName]!!
            this.methodVisitor.visitVarInsn(Opcodes.ALOAD, location)
            this.methodVisitor.visitInsn(Opcodes.SWAP)

            this.methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.ownerType.getInternalName(), field.name, field.type.getDescriptor())
        } else {
            methodVisitor.visitVarInsn(Opcodes.ISTORE, location)
        }
    }
}