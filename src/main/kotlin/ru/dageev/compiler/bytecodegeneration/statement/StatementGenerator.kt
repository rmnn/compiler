package ru.dageev.compiler.bytecodegeneration.statement

import jdk.internal.org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.*
import ru.dageev.compiler.domain.node.statement.*
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/26/16.
 */
class StatementGenerator(scope: Scope, classesContext: ClassesContext, methodVisitor: MethodVisitor) {

    val expressionGenerator: ExpressionGenerator
    val printStatementGenerator: PrintStatementGenerator
    val variableDeclarationGenerator: VariableDeclarationGenerator
    val assignmentStatementGenerator: AssignmentStatementGenerator
    val blockStatementGenerator: BlockStatementGenerator
    val returnStatementGenerator: ReturnStatementGenerator
    val ifStatementGenerator: IfStatementGenerator
    val whileStatementGenerator: WhileStatementGenerator
    val readStatementGenerator: ReadStatementGenerator


    init {
        expressionGenerator = ExpressionGenerator(scope, classesContext, methodVisitor)
        printStatementGenerator = PrintStatementGenerator(methodVisitor, expressionGenerator)
        variableDeclarationGenerator = VariableDeclarationGenerator(scope, this, expressionGenerator)
        assignmentStatementGenerator = AssignmentStatementGenerator(scope, classesContext, methodVisitor, expressionGenerator)
        blockStatementGenerator = BlockStatementGenerator(classesContext, methodVisitor)
        returnStatementGenerator = ReturnStatementGenerator(expressionGenerator, methodVisitor)
        ifStatementGenerator = IfStatementGenerator(this, expressionGenerator, methodVisitor)
        whileStatementGenerator = WhileStatementGenerator(this, expressionGenerator, methodVisitor)
        readStatementGenerator = ReadStatementGenerator(scope, classesContext, methodVisitor)
    }

    fun generate(assignment: Assignment) {
        assignmentStatementGenerator.generate(assignment)
    }

    fun generate(block: Block) {
        blockStatementGenerator.generate(block)
    }

    fun generate(ifStatement: IfStatement) {
        ifStatementGenerator.generate(ifStatement)
    }

    fun generate(printStatement: PrintStatement) {
        printStatementGenerator.generate(printStatement)
    }


    fun generate(readStatement: ReadStatement) {
        readStatementGenerator.generate(readStatement)
    }

    fun generate(returnStatement: ReturnStatement) {
        returnStatementGenerator.generate(returnStatement)
    }

    fun generate(whileStatement: WhileStatement) {
        whileStatementGenerator.generate(whileStatement)
    }

    fun generate(variableDeclaration: VariableDeclaration) {
        variableDeclarationGenerator.generate(variableDeclaration)
    }

    fun generate(additional: BinaryExpression.AdditionalExpression) {
        additional.accept(expressionGenerator)
    }

    fun generate(division: BinaryExpression.DivisionalExpression) {
        division.accept(expressionGenerator)
    }

    fun generate(equality: BinaryExpression.EqualityExpression) {
        equality.accept(expressionGenerator)
    }

    fun generate(module: BinaryExpression.ModuleExpression) {
        module.accept(expressionGenerator)
    }

    fun generate(multiplication: BinaryExpression.MultiplicationExpression) {
        multiplication.accept(expressionGenerator)
    }

    fun generate(subtractionExpression: BinaryExpression.SubtractionExpression) {
        subtractionExpression.accept(expressionGenerator)
    }

    fun generate(greater: BinaryExpression.GreaterExpression) {
        greater.accept(expressionGenerator)
    }

    fun generate(greaterEquals: BinaryExpression.GreaterEqualsExpression) {
        greaterEquals.accept(expressionGenerator)
    }

    fun generate(less: BinaryExpression.LessExpression) {
        less.accept(expressionGenerator)
    }

    fun generate(lessEquals: BinaryExpression.LessEqualsExpression) {
        lessEquals.accept(expressionGenerator)
    }

    fun generate(or: BinaryExpression.LogicalOrExpression) {
        or.accept(expressionGenerator)
    }

    fun generate(and: BinaryExpression.LogicalAndExpression) {
        and.accept(expressionGenerator)
    }

    fun generate(nonEquality: BinaryExpression.NonEqualityExpression) {
        nonEquality.accept(expressionGenerator)
    }

    fun generate(methodCall: Call.MethodCall) {
        methodCall.accept(expressionGenerator)
    }

    fun generate(constructorCall: Call.ConstructorCall) {
        constructorCall.accept(expressionGenerator)
    }

    fun generate(superCall: Call.SuperCall) {
        superCall.accept(expressionGenerator)
    }

    fun generate(emptyExpression: EmptyExpression) {
        emptyExpression.accept(expressionGenerator)
    }

    fun generate(fieldAccess: FieldAccess) {
        fieldAccess.accept(expressionGenerator)
    }

    fun generate(parameter: Parameter) {
        parameter.accept(expressionGenerator)
    }

    fun generate(value: Value) {
        value.accept(expressionGenerator)
    }

    fun generate(localVariableReference: VariableReference.LocalVariableReference) {
        localVariableReference.accept(expressionGenerator)
    }

    fun generate(fieldReference: VariableReference.FieldReference) {
        fieldReference.accept(expressionGenerator)
    }
}








