package ru.dageev.compiler.parser.visitor.statement

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.BinaryExpression
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.domain.node.statement.Statement
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class StatementVisitor(val scope: Scope, val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<Statement>() {


    override fun visitPrint(@NotNull ctx: ElaginParser.PrintContext): Statement {
        return PrintStatementVisitor(ExpressionVisitor(scope, classesContext)).visitPrint(ctx)
    }

    override fun visitRead(ctx: ElaginParser.ReadContext): Statement {
        return ReadStatementVisitor().visitRead(ctx)
    }

    override fun visitReturnStatement(ctx: ElaginParser.ReturnStatementContext): Statement {
        return ReturnStatementVisitor(ExpressionVisitor(scope, classesContext)).visitReturnStatement(ctx)
    }

    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): Statement {
        return LocalVariableDeclarationVisitor(scope, typeProvider, ExpressionVisitor(scope, classesContext)).visitLocalVariableDeclarationStatement(ctx)
    }

    override fun visitBlock(ctx: ElaginParser.BlockContext): Statement {
        return BlockStatementVisitor(scope, typeProvider, classesContext).visitBlock(ctx)
    }

    override fun visitIfStatement(ctx: ElaginParser.IfStatementContext): Statement {
        return IfStatementVisitor(scope, typeProvider, classesContext).visitIfStatement(ctx)
    }


    override fun visitWhileStatement(ctx: ElaginParser.WhileStatementContext): Statement {
        return WhileStatementVisitor(scope, typeProvider, classesContext).visitWhileStatement(ctx)
    }

    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Statement {
        return AssignmentStatementVisitor(scope, classesContext, ExpressionVisitor(scope, classesContext)).visitAssignment(ctx)
    }


    override fun visitVariableReference(ctx: ElaginParser.VariableReferenceContext): Expression {
        return ExpressionVisitor(scope, classesContext).visitVariableReference(ctx)
    }


    override fun visitBooleanValue(ctx: ElaginParser.BooleanValueContext): Value {
        return ExpressionVisitor(scope, classesContext).visitBooleanValue(ctx)

    }

    override fun visitIntegerValue(ctx: ElaginParser.IntegerValueContext): Value {
        return ExpressionVisitor(scope, classesContext).visitIntegerValue(ctx)
    }

    override fun visitMethodCall(ctx: ElaginParser.MethodCallContext): Expression {
        return ExpressionVisitor(scope, classesContext).visitMethodCall(ctx)
    }

    override fun visitSuperCall(ctx: ElaginParser.SuperCallContext): Expression {
        return ExpressionVisitor(scope, classesContext).visitSuperCall(ctx)
    }

    override fun visitConstructorCall(ctx: ElaginParser.ConstructorCallContext): Expression {
        return ExpressionVisitor(scope, classesContext).visitConstructorCall(ctx)
    }


    override fun visitFieldAccessor(ctx: ElaginParser.FieldAccessorContext): Expression {
        return ExpressionVisitor(scope, classesContext).visitFieldAccessor(ctx)
    }

    override fun visitMultDivExpression(ctx: ElaginParser.MultDivExpressionContext): BinaryExpression {
        return ExpressionVisitor(scope, classesContext).visitMultDivExpression(ctx)
    }


    override fun visitSumExpression(ctx: ElaginParser.SumExpressionContext): BinaryExpression {
        return ExpressionVisitor(scope, classesContext).visitSumExpression(ctx)
    }

    override fun visitCompareExpression(ctx: ElaginParser.CompareExpressionContext): BinaryExpression {
        return ExpressionVisitor(scope, classesContext).visitCompareExpression(ctx)
    }

    override fun visitLogicalExpression(ctx: ElaginParser.LogicalExpressionContext): BinaryExpression {
        return ExpressionVisitor(scope, classesContext).visitLogicalExpression(ctx)
    }


}