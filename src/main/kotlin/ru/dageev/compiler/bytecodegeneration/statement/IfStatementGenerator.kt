package ru.dageev.compiler.bytecodegeneration.statement

import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.IfStatement

/**
 * Created by dageev
 * on 11/27/16.
 */
class IfStatementGenerator(val statementGenerator: StatementGenerator,
                           val expressionGenerator: ExpressionGenerator,
                           val methodVisitor: MethodVisitor) {


    fun generate(ifStatement: IfStatement) {
        ifStatement.condition.accept(expressionGenerator)
        val (trueLabel, endLabel) = Label() to Label()
        methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel)
        ifStatement.elseStatement.ifPresent {
            it.accept(statementGenerator)
        }
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(trueLabel)
        ifStatement.trueStatement.accept(statementGenerator)
        methodVisitor.visitLabel(endLabel)
    }
}