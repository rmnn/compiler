package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.ReturnStatement

/**
 * Created by dageev
 * on 11/27/16.
 */
class ReturnStatementGenerator(val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {
    fun generate(returnStatement: ReturnStatement) {
        returnStatement.expression.accept(expressionGenerator)
        methodVisitor.visitInsn(returnStatement.expression.type.getReturnOpcode())
    }
}