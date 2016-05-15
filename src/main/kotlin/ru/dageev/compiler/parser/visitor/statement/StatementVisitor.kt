package ru.dageev.compiler.parser.visitor.statement

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class StatementVisitor(scope: Scope) : ElaginBaseVisitor<Statement>() {
    val expressionVisitor = ExpressionVisitor(scope)

    override fun visitPrint(@NotNull ctx: ElaginParser.PrintContext): Statement {
        return PrintStatementVisitor(expressionVisitor).visitPrint(ctx)
    }

    override fun visitRead(ctx: ElaginParser.ReadContext): Statement {
        return ReadStatementVisitor().visitRead(ctx)
    }

//    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): List<Statement> {
//        return LocalVariableDeclarationVisitor(expressionVisitor).visitLocalVariableDeclarationStatement(ctx)
//    }


}