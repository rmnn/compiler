package ru.dageev.compiler.parser.helper

import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.expression.BinaryExpression
import ru.dageev.compiler.domain.node.expression.Call
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.domain.node.statement.*
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
class TailRecOptimizationHelper {
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
        if (!correctTailrec) {
            throw CompilationException("Function marked as tailrec but no tailrec call found for '${method.methodSignature}'")
        }


        val trueConditional = Value("true", PrimitiveType.BOOLEAN)
        val whileStatement = WhileStatement(trueConditional, replaceTailRecCallWithAssignment(block.scope, method.methodSignature, method.statement))
        val statements = listOf(whileStatement, getReturnStatement(method.methodSignature.returnType))
        return MethodDeclaration(method.methodSignature, Block(block.scope, statements))

    }

    private fun getReturnStatement(type: Type): ReturnStatement {
        return when (type) {
            PrimitiveType.BOOLEAN -> ReturnStatement(Value("true", type))
            PrimitiveType.INT -> ReturnStatement(Value("0", type))
            else -> throw CompilationException("tailrec supported only for primitive types")
        }
    }

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

    private fun replaceTailRecCallWithAssignmentCall(scope: Scope, methodSignature: MethodSignature, call: Call.MethodCall): Statement {
        if (!ownMethodCall(methodSignature, call)) {
            return ReturnStatement(call)
        }

        val assignments = call.arguments.mapIndexed { i, argument ->
            val parameterName = methodSignature.parameters[i].name
            Assignment(Optional.empty(), parameterName, argument.expression)
        }
        return Block(scope, assignments)
    }

    private fun isRecursive(methodSignature: MethodSignature, expression: Expression): Boolean {
        return when (expression) {
            is BinaryExpression -> isRecursiveAndReturnStatementExpression(methodSignature, expression.rightExpression)
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