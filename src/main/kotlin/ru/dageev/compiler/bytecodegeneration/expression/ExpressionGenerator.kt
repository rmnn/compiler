package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.*
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/26/16.
 */
class ExpressionGenerator(val scope: Scope, val classesContext: ClassesContext, val methodVisitor: MethodVisitor) {

    val fieldReferenceGenerator: FieldReferenceGenerator
    val parameterExpressionGenerator: ParameterExpressionGenerator
    val valueExpressionGenerator: ValueExpressionGenerator
    val callGenerator: CallGenerator
    val fieldAccessGenerator: FieldAccessGenerator
    val binaryOperationGenerator: BinaryOperationGenerator

    init {
        fieldReferenceGenerator = FieldReferenceGenerator(scope, methodVisitor)
        parameterExpressionGenerator = ParameterExpressionGenerator(scope, methodVisitor)
        valueExpressionGenerator = ValueExpressionGenerator(methodVisitor)
        callGenerator = CallGenerator(scope, classesContext, this, methodVisitor)
        fieldAccessGenerator = FieldAccessGenerator(methodVisitor)
        binaryOperationGenerator = BinaryOperationGenerator(this, methodVisitor)
    }

    fun generate(fieldReference: VariableReference.FieldReference) {
        fieldReferenceGenerator.generate(fieldReference)
    }

    fun generate(localVariableReference: VariableReference.LocalVariableReference) {
        fieldReferenceGenerator.generate(localVariableReference)
    }

    fun generate(parameter: Parameter) {
        parameterExpressionGenerator.generate(parameter)
    }

    fun generate(value: Value) {
        valueExpressionGenerator.generate(value)
    }

    fun generate(constructorCall: Call.ConstructorCall) {
        callGenerator.generate(constructorCall)
    }

    fun generate(methodCall: Call.MethodCall) {
        callGenerator.generate(methodCall)
    }

    fun generate(superCall: Call.SuperCall) {
        callGenerator.generate(superCall)
    }

    fun generate(fieldAccess: FieldAccess) {
        fieldAccessGenerator.generate(fieldAccess)
    }


    fun generate(additional: BinaryExpression.AdditionalExpression) {
        binaryOperationGenerator.generate(additional)
    }

    fun generate(division: BinaryExpression.DivisionalExpression) {
        binaryOperationGenerator.generate(division)
    }

    fun generate(equality: BinaryExpression.EqualityExpression) {
        binaryOperationGenerator.generate(equality)
    }

    fun generate(module: BinaryExpression.ModuleExpression) {
        binaryOperationGenerator.generate(module)
    }

    fun generate(multiplication: BinaryExpression.MultiplicationExpression) {
        binaryOperationGenerator.generate(multiplication)
    }

    fun generate(subtractionExpression: BinaryExpression.SubtractionExpression) {
        binaryOperationGenerator.generate(subtractionExpression)
    }

    fun generate(greater: BinaryExpression.GreaterExpression) {
        binaryOperationGenerator.generate(greater)
    }

    fun generate(greaterEquals: BinaryExpression.GreaterEqualsExpression) {
        binaryOperationGenerator.generate(greaterEquals)
    }

    fun generate(less: BinaryExpression.LessExpression) {
        binaryOperationGenerator.generate(less)
    }

    fun generate(lessEquals: BinaryExpression.LessEqualsExpression) {
        binaryOperationGenerator.generate(lessEquals)
    }

    fun generate(or: BinaryExpression.LogicalOrExpression) {
        binaryOperationGenerator.generate(or)

    }

    fun generate(and: BinaryExpression.LogicalAndExpression) {
        binaryOperationGenerator.generate(and)
    }

    fun generate(nonEquality: BinaryExpression.NonEqualityExpression) {
        binaryOperationGenerator.generate(nonEquality)
    }

    fun generate(emptyExpression: EmptyExpression) {

    }


}