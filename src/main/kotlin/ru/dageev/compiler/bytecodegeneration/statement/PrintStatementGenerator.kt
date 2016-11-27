package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.PrintStatement
import ru.dageev.compiler.domain.type.ClassType

/**
 * Created by dageev
 * on 11/27/16.
 */
class PrintStatementGenerator(val methodVisitor: MethodVisitor, val expressionGenerator: ExpressionGenerator) {

    fun generate(printStatement: PrintStatement) {
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;")
        printStatement.expression.accept(expressionGenerator)
        val descriptor = "(" + printStatement.expression.type.getDescriptor() + ")V"
        val owner = ClassType("java.io.PrintStream")
        val fieldDescriptor = owner.getDescriptor()
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, fieldDescriptor, "println", descriptor, false)
    }
}