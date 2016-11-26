package ru.dageev.compiler.bytecodegeneration.method

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.expression.EmptyExpression
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.node.statement.ReturnStatement
import ru.dageev.compiler.domain.scope.MethodSignature

/**
 * Created by dageev
 * on 11/26/16.
 */
open class AbstractMethodGenerator() {
    fun getMethodDescriptor(methodSignature: MethodSignature): String {
        val parametersDescriptor = methodSignature.parameters.map { it.type.getDescriptor() }.joinToString(separator = "", prefix = "(", postfix = ")")
        val returnType = methodSignature.returnType.getDescriptor()
        return parametersDescriptor + returnType
    }

    fun appendReturnIfNotExists(method: MethodDeclaration, block: Block, statementGenerator: StatementGenerator) {
        val isLastStatementReturn = block.statements.isEmpty() || block.statements.last() is ReturnStatement
        if (!isLastStatementReturn) {
            val emptyExpression = EmptyExpression(method.methodSignature.returnType)
            val returnStatement = ReturnStatement(emptyExpression)
            returnStatement.accept(statementGenerator)
        }
    }
}