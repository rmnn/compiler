package ru.dageev.compiler.parser.visitor.statement

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class StatementVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<Statement>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    val expressionVisitor = ExpressionVisitor(this.scope, classesContext)

    override fun visitPrint(@NotNull ctx: ElaginParser.PrintContext): Statement {
        return PrintStatementVisitor(expressionVisitor).visitPrint(ctx)
    }

    override fun visitRead(ctx: ElaginParser.ReadContext): Statement {
        return ReadStatementVisitor().visitRead(ctx)
    }

    override fun visitReturnStatement(ctx: ElaginParser.ReturnStatementContext): Statement {
        return ReturnStatementVisitor(expressionVisitor).visitReturnStatement(ctx)
    }

    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): Statement {
        return LocalVariableDeclarationVisitor(expressionVisitor).visitLocalVariableDeclarationStatement(ctx)
    }

    override fun visitBlock(ctx: ElaginParser.BlockContext): Statement {
        return BlockStatementVisitor(scope, classesContext).visitBlock(ctx)
    }

    override fun visitIfStatement(ctx: ElaginParser.IfStatementContext): Statement {
        return IfStatementVisitor(scope, classesContext).visitIfStatement(ctx)
    }


    override fun visitWhileStatement(ctx: ElaginParser.WhileStatementContext): Statement {
        return WhileStatementVisitor(scope, classesContext).visitWhileStatement(ctx)
    }

    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Statement {
        return AssignmentStatementVisitor(scope, expressionVisitor).visitAssignment(ctx)
    }


}