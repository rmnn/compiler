package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.WhileStatement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 * on 10/30/16.
 */
class WhileStatementVisitor(scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<WhileStatement>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitWhileStatement(ctx: ElaginParser.WhileStatementContext): WhileStatement {
        val parExpression = ctx.parExpression().expression().accept(ExpressionVisitor(scope, classesContext))
        if (parExpression.type != PrimitiveType.BOOLEAN) {
            throw CompilationException("Type ${parExpression.type} could not be used for condition")
        }
        val body = ctx.statement().accept(StatementVisitor(scope, typeProvider, classesContext))
        return WhileStatement(parExpression, body)
    }
}