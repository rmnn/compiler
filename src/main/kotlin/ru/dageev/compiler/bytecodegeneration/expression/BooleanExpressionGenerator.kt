package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.Label
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.CompareSign.*
import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.node.expression.*
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 * on 11/30/16.
 */
class BooleanExpressionGenerator(val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {

    fun generate(operation: BinaryExpression, compareSign: CompareSign) {
        if (isBetweenPrimitives(operation)) {
            generatePrimitivesComparison(operation.leftExpression, operation.rightExpression)
        } else {
            if (isBetweenClasses(operation)) {
                generateObjectsComparison(operation.leftExpression, operation.rightExpression, compareSign)
            } else {
                throw  CompilationException("Mixed boolean expression for type ${operation.leftExpression.type} and ${operation.rightExpression.type} is not supported")
            }
        }

        val startLabel = Label()
        val endLabel = Label()
        methodVisitor.visitJumpInsn(compareSign.opCode, startLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_0)
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel)
        methodVisitor.visitLabel(startLabel)
        methodVisitor.visitInsn(Opcodes.ICONST_1)
        methodVisitor.visitLabel(endLabel)
    }

    private fun isBetweenPrimitives(operation: BinaryExpression) = operation.leftExpression.type is PrimitiveType && operation.rightExpression.type is PrimitiveType

    private fun isBetweenClasses(operation: BinaryExpression) = operation.leftExpression.type is ClassType && operation.rightExpression.type is ClassType

    private fun generateObjectsComparison(left: Expression, right: Expression, compareSign: CompareSign) {
        val parameter = Parameter("o", ClassType("java.lang.Object"))
        val parameters = listOf(parameter)
        val argument = Argument(right)
        val arguments = listOf(argument)
        when (compareSign) {
            EQUALS, NOT_EQUALS -> {
                val equalsSignature = MethodSignature(AccessModifier.PUBLIC, "equals", parameters, PrimitiveType.BOOLEAN)
                val equalsCall = Call.MethodCall(equalsSignature, "equals", arguments, left)
                equalsCall.accept(expressionGenerator)
                methodVisitor.visitInsn(Opcodes.ICONST_1)
                methodVisitor.visitInsn(Opcodes.IXOR)
            }
            LESS, GREATER, LESS_EQUALS, GREATER_EQUALS -> {
                val compareToSignature = MethodSignature(AccessModifier.PUBLIC, "compareTo", parameters, PrimitiveType.INT)
                val compareToCall = Call.MethodCall(compareToSignature, "compareTo", arguments, left)
                compareToCall.accept(expressionGenerator)
            }
        }
    }

    private fun generatePrimitivesComparison(left: Expression, right: Expression) {
        left.accept(expressionGenerator)
        right.accept(expressionGenerator)
        methodVisitor.visitInsn(Opcodes.ISUB)
    }
}