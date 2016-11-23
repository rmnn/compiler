package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.IfStatement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor
import java.util.*

/**
 * Created by dageev
 * on 10/30/16.
 */
class IfStatementVisitor(scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<IfStatement>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitIfStatement(ctx: ElaginParser.IfStatementContext): IfStatement {
        val parExpression = ctx.parExpression().expression().accept(ExpressionVisitor(scope, classesContext))
        if (parExpression.type != PrimitiveType.BOOLEAN) {
            throw CompilationException("Type ${parExpression.type} could not be used for condition")
        }
        val ifStatement = ctx.statement()[0].accept(StatementVisitor(scope, typeProvider, classesContext))
        val elseStatement = if (ctx.statement().size > 1) {
            Optional.of(ctx.statement()[1].accept(StatementVisitor(scope, typeProvider, classesContext)))
        } else {
            Optional.empty()
        }

        return IfStatement(parExpression, ifStatement, elseStatement)
    }
}