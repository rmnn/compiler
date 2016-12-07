package ru.dageev.compiler.bytecodegeneration.statement

import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.WhileStatement

/**
 * Created by dageev
 * on 11/27/16.
 */
class WhileStatementGenerator(val statementGenerator: StatementGenerator, val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {

    fun generate(whileStatement: WhileStatement) {
        val (start, end) = Label() to Label()
        methodVisitor.visitLabel(start)
        whileStatement.condition.accept(expressionGenerator)
        methodVisitor.visitJumpInsn(Opcodes.IFEQ, end);
        whileStatement.body.accept(statementGenerator)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, start);
        methodVisitor.visitLabel(end)
    }
}