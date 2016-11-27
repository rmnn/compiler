package ru.dageev.compiler.bytecodegeneration.statement

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.domain.node.statement.VariableDeclaration
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType

/**
 * Created by dageev
 * on 11/27/16.
 */
class VariableDeclarationGenerator(val scope: Scope, val statementGenerator: StatementGenerator, val expressionGenerator: ExpressionGenerator) {

    fun generate(variableDeclaration: VariableDeclaration) {
        variableDeclaration.expression.accept(expressionGenerator)
        val assignment = Assignment(ClassType(scope.className), variableDeclaration.name, variableDeclaration.expression)
        assignment.accept(statementGenerator)
    }
}