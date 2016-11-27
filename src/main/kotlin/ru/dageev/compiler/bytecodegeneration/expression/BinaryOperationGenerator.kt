package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.expression.BinaryExpression

/**
 * Created by dageev
 * on 11/27/16.
 */
class BinaryOperationGenerator(val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {

    fun generate(additional: BinaryExpression.AdditionalExpression) {
        evaluateOperation(additional, Opcodes.IADD)
    }

    fun generate(division: BinaryExpression.DivisionalExpression) {
        evaluateOperation(division, Opcodes.IDIV)

    }

    fun generate(subtractionExpression: BinaryExpression.SubtractionExpression) {
        evaluateOperation(subtractionExpression, Opcodes.ISUB)
    }

    fun generate(multiplication: BinaryExpression.MultiplicationExpression) {
        evaluateOperation(multiplication, Opcodes.IMUL)
    }

    fun generate(module: BinaryExpression.ModuleExpression) {
        evaluateOperation(module, Opcodes.IREM)
    }

    // TODO INCORRECT, REWRITE
    fun generate(equality: BinaryExpression.EqualityExpression) {
        evaluateOperation(equality, Opcodes.IF_ACMPEQ)
    }

    fun generate(greater: BinaryExpression.GreaterExpression) {
        evaluateOperation(greater, Opcodes.IF_ICMPGT)
    }

    fun generate(greaterEquals: BinaryExpression.GreaterEqualsExpression) {
        evaluateOperation(greaterEquals, Opcodes.IF_ICMPGE)
    }

    fun generate(less: BinaryExpression.LessExpression) {
        evaluateOperation(less, Opcodes.IF_ICMPLT)
    }

    fun generate(lessEquals: BinaryExpression.LessEqualsExpression) {
        evaluateOperation(lessEquals, Opcodes.IF_ICMPLE)
    }

    fun generate(nonEquality: BinaryExpression.NonEqualityExpression) {
        evaluateOperation(nonEquality, Opcodes.IF_ACMPNE)

    }

    fun generate(or: BinaryExpression.LogicalOrExpression) {
        evaluateOperation(or, Opcodes.IOR)

    }

    fun generate(and: BinaryExpression.LogicalAndExpression) {
        evaluateOperation(and, Opcodes.IAND)

    }

    private fun evaluateOperation(expression: BinaryExpression, operationCode: Int) {
        expression.leftExpression.accept(expressionGenerator)
        expression.rightExpression.accept(expressionGenerator)
        methodVisitor.visitInsn(operationCode)
    }
}