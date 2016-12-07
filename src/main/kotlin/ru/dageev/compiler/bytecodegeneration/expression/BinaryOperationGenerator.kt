package ru.dageev.compiler.bytecodegeneration.expression

import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.node.expression.BinaryExpression

/**
 * Created by dageev
 * on 11/27/16.
 */
class BinaryOperationGenerator(val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {

    val booleanExpressionGenerator: BooleanExpressionGenerator

    init {
        booleanExpressionGenerator = BooleanExpressionGenerator(expressionGenerator, methodVisitor)
    }

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

    fun generate(equality: BinaryExpression.EqualityExpression) {
        booleanExpressionGenerator.generate(equality, CompareSign.EQUALS)
    }

    fun generate(greater: BinaryExpression.GreaterExpression) {
        booleanExpressionGenerator.generate(greater, CompareSign.GREATER)
    }

    fun generate(greaterEquals: BinaryExpression.GreaterEqualsExpression) {
        booleanExpressionGenerator.generate(greaterEquals, CompareSign.GREATER_EQUALS)
    }

    fun generate(less: BinaryExpression.LessExpression) {
        booleanExpressionGenerator.generate(less, CompareSign.LESS)
    }

    fun generate(lessEquals: BinaryExpression.LessEqualsExpression) {
        booleanExpressionGenerator.generate(lessEquals, CompareSign.LESS_EQUALS)
    }

    fun generate(nonEquality: BinaryExpression.NonEqualityExpression) {
        booleanExpressionGenerator.generate(nonEquality, CompareSign.NOT_EQUALS)
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