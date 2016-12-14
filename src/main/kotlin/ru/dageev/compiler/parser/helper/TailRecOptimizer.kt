package ru.dageev.compiler.parser.helper

import org.slf4j.LoggerFactory
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.expression.*
import ru.dageev.compiler.domain.node.statement.*
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.parser.CompilationException
import java.util.*

/**
 * Created by dageev
 * on 12/11/16.
 */
class TailRecOptimizer {

    companion object {
        val logger = LoggerFactory.getLogger(TailRecOptimizer::class.java)
    }

    fun generate(method: MethodDeclaration): MethodDeclaration {
        assert(method.methodSignature.tailrec)

        val block = method.statement as Block
        if (block.statements.isEmpty()) {
            throw  CompilationException("Unable to do tailrec optimization for ${method.methodSignature}")
        }

        val returnStatements = getReturnStatements(block)
        val isRecursive = returnStatements.any { isRecursive(method.methodSignature, it.expression) }
        if (!isRecursive) {
            throw CompilationException("Unable to do tailrec optimization for non recursive function '${method.methodSignature}'")
        }

        val correctTailrec = returnStatements.all { isRecursiveAndReturnStatementExpression(method.methodSignature, it.expression) }
        if (correctTailrec) {
            logger.info("Tailrec optimization found for '${method.methodSignature}'")
            val trueConditional = Value("true", PrimitiveType.BOOLEAN)
            val whileStatement = WhileStatement(trueConditional, replaceTailRecCallWithAssignment(block.scope, method.methodSignature, method.statement))
            val statements = listOf(whileStatement, getReturnStatement(method.methodSignature.returnType))
            return MethodDeclaration(method.methodSignature, Block(block.scope, statements))
        } else {
            val containsPotentialAccumulator = returnStatements.any { accumulatorCouldBeExtracted(method.methodSignature, it.expression) }
            val simpleReturnStatements = returnStatements.filter { !accumulatorCouldBeExtracted(method.methodSignature, it.expression) }
            val mayDetectStartAccumulator = simpleReturnStatements.size == 1
            if (containsPotentialAccumulator && mayDetectStartAccumulator) {
                logger.info("Tailrec optimization with accumulator detection found for '${method.methodSignature}'")
                val accumulator = VariableDeclaration("elaginAccum", method.methodSignature.returnType, simpleReturnStatements[0].expression)

                val trueConditional = Value("true", PrimitiveType.BOOLEAN)
                val whileStatement = WhileStatement(trueConditional, replaceTailRecCallWithAccumAssignment(block.scope, method.methodSignature,
                        method.statement, simpleReturnStatements[0]))
                val statements = listOf(accumulator, whileStatement, getAccumReturnStatement(method.methodSignature.returnType))
                return MethodDeclaration(method.methodSignature, Block(block.scope, statements))
            } else {
                throw CompilationException("Function marked as tailrec but no tailrec calls found for ${method.methodSignature}")
            }
        }

    }


    private fun getReturnStatement(type: Type): ReturnStatement {
        return when (type) {
            PrimitiveType.BOOLEAN -> ReturnStatement(Value("true", type))
            PrimitiveType.INT -> ReturnStatement(Value("0", type))
            else -> throw CompilationException("tailrec supported only for primitive types")
        }
    }

    private fun getAccumReturnStatement(returnType: Type): ReturnStatement {
        return ReturnStatement(getAccumReference(returnType))
    }

    private fun getAccumReference(returnType: Type) = VariableReference.LocalVariableReference(LocalVariable("elaginAccum", returnType))

    private fun replaceTailRecCallWithAssignment(scope: Scope, methodSignature: MethodSignature, statement: Statement): Statement {
        return when (statement) {
            is Block -> Block(statement.scope, statement.statements.map { replaceTailRecCallWithAssignment(scope, methodSignature, it) })
            is IfStatement -> IfStatement(statement.condition, replaceTailRecCallWithAssignment(scope, methodSignature, statement.trueStatement),
                    statement.elseStatement.map { replaceTailRecCallWithAssignment(scope, methodSignature, it) })
            is WhileStatement -> WhileStatement(statement.condition, replaceTailRecCallWithAssignment(scope, methodSignature, statement.body))
            is ReturnStatement ->
                when (statement.expression) {
                    is Call.MethodCall -> replaceTailRecCallWithAssignmentCall(scope, methodSignature, statement.expression)
                    else -> statement
                }
            else -> statement
        }
    }


    private fun replaceTailRecCallWithAccumAssignment(scope: Scope, methodSignature: MethodSignature, statement: Statement, accumReturn: ReturnStatement): Statement {
        return when (statement) {
            is Block -> Block(statement.scope, statement.statements.map { replaceTailRecCallWithAccumAssignment(scope, methodSignature, it, accumReturn) })
            is IfStatement -> IfStatement(statement.condition, replaceTailRecCallWithAccumAssignment(scope, methodSignature, statement.trueStatement, accumReturn),
                    statement.elseStatement.map { replaceTailRecCallWithAccumAssignment(scope, methodSignature, it, accumReturn) })
            is WhileStatement -> WhileStatement(statement.condition, replaceTailRecCallWithAccumAssignment(scope, methodSignature, statement.body, accumReturn))
            is ReturnStatement ->
                if (accumReturn == statement) {
                    getAccumReturnStatement(methodSignature.returnType)
                } else {
                    replaceTailRec(scope, methodSignature, statement.expression)
                }

            else -> statement
        }
    }

    private fun replaceTailRec(scope: Scope, methodSignature: MethodSignature, expression: Expression): Statement {
        val patched = replaceOwnCallWithAccumReference(scope, methodSignature, expression)
        val assignment = Assignment(Optional.empty(), "elaginAccum", patched)
        val assignments = replaceTailRecWithAssignments(methodSignature, expression)
        return Block(scope, listOf(assignment) + assignments)

    }

    private fun replaceTailRecWithAssignments(methodSignature: MethodSignature, expression: Expression): List<Statement> {
        when (expression) {
            is BinaryExpression -> replaceTailRecWithAssignments(methodSignature, expression.leftExpression) +
                    replaceTailRecWithAssignments(methodSignature, expression.rightExpression)
            is Call.MethodCall -> if (ownMethodCall(methodSignature, expression)) {
                getAssignments(expression, methodSignature)
            } else {
                emptyList()
            }
            else -> emptyList()
        }

        if (expression is BinaryExpression) {
            return replaceTailRecWithAssignments(methodSignature, expression.leftExpression) + replaceTailRecWithAssignments(methodSignature, expression.rightExpression)
        }
        return if (expression is Call.MethodCall && ownMethodCall(methodSignature, expression)) {
            expression.arguments.mapIndexed { i, argument ->
                val parameterName = methodSignature.parameters[i].name
                Assignment(Optional.empty(), parameterName, argument.expression)
            }
        } else {
            emptyList()
        }
    }

    private fun getAssignments(expression: Call.MethodCall, methodSignature: MethodSignature): List<Statement> {
        return expression.arguments.mapIndexed { i, argument ->
            val parameterName = methodSignature.parameters[i].name
            Assignment(Optional.empty(), parameterName, argument.expression)
        }
    }

    private fun replaceOwnCallWithAccumReference(scope: Scope, methodSignature: MethodSignature, expression: Expression): Expression {
        return when (expression) {
            is BinaryExpression -> expression.copy(replaceOwnCallWithAccumReference(scope, methodSignature, expression.leftExpression),
                    replaceOwnCallWithAccumReference(scope, methodSignature, expression.rightExpression))
            is Call.MethodCall -> if (ownMethodCall(methodSignature, expression)) getAccumReference(methodSignature.returnType) else expression
            else -> expression
        }
    }


    private fun replaceTailRecCallWithAssignmentCall(scope: Scope, methodSignature: MethodSignature, call: Call.MethodCall): Statement {
        if (!ownMethodCall(methodSignature, call)) {
            return ReturnStatement(call)
        }

        val assignments = getAssignments(call, methodSignature)
        return Block(scope, assignments)
    }

    private fun isRecursive(methodSignature: MethodSignature, expression: Expression): Boolean {
        return when (expression) {
            is BinaryExpression -> isRecursive(methodSignature, expression.rightExpression) ||
                    isRecursive(methodSignature, expression.leftExpression)
            is Call.MethodCall -> (ownMethodCall(methodSignature, expression)) || expression.arguments.any { isRecursive(methodSignature, it.expression) }
            is Call.ConstructorCall -> expression.arguments.any { isRecursive(methodSignature, it.expression) }
            else -> false
        }
    }

    private fun isRecursiveAndReturnStatementExpression(methodSignature: MethodSignature, expression: Expression, mayContainsOwnCall: Boolean = true): Boolean {
        return when (expression) {
            is BinaryExpression -> isRecursiveAndReturnStatementExpression(methodSignature, expression.leftExpression, false) &&
                    isRecursiveAndReturnStatementExpression(methodSignature, expression.rightExpression, false)
            is Call.MethodCall -> if (ownMethodCall(methodSignature, expression)) mayContainsOwnCall else true
            else -> true
        }
    }

    private fun accumulatorCouldBeExtracted(methodSignature: MethodSignature, expression: Expression): Boolean {
        return when (expression) {
            is BinaryExpression -> accumulatorCouldBeExtracted(methodSignature, expression.leftExpression) ||
                    accumulatorCouldBeExtracted(methodSignature, expression.rightExpression)
            is Call.MethodCall -> ownMethodCall(methodSignature, expression)
            else -> false
        }
    }

    private fun ownMethodCall(methodSignature: MethodSignature, call: Call.MethodCall) = call.methodSignature == methodSignature

    fun getReturnStatements(statement: Statement): List<ReturnStatement> {
        return when (statement) {
            is Block -> statement.statements.map { getReturnStatements(it) }.flatten()
            is ReturnStatement -> listOf(statement)
            is IfStatement -> getReturnStatements(statement.trueStatement) + statement.elseStatement.map { getReturnStatements(it) }.orElse(emptyList())
            is WhileStatement -> getReturnStatements(statement.body)
            else -> emptyList()
        }
    }
}