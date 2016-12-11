package ru.dageev.compiler.bytecodegeneration.method

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.expression.EmptyExpression
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.node.statement.ReturnStatement
import ru.dageev.compiler.domain.type.PrimitiveType

/**
 * Created by dageev
 * on 11/26/16.
 */
open class AbstractMethodGenerator() {
    fun appendReturnIfNotExists(method: MethodDeclaration, block: Block, statementGenerator: StatementGenerator) {
        val isLastStatementReturn = !block.statements.isEmpty() && block.statements.last() is ReturnStatement
        if (!isLastStatementReturn && method.methodSignature.returnType == PrimitiveType.VOID) {
            val emptyExpression = EmptyExpression(method.methodSignature.returnType)
            val returnStatement = ReturnStatement(emptyExpression)
            returnStatement.accept(statementGenerator)
        }
    }
}