package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.BinaryExpression
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ExpressionVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<Expression>() {
    val scope: Scope

    val binaryOperationVisitor = BinaryOperationVisitor(this)

    init {
        this.scope = scope.copy()
    }

    override fun visitVariableReference(ctx: ElaginParser.VariableReferenceContext): Expression {
        return VariableReferenceVisitor(scope, classesContext).visitVariableReference(ctx)
    }


    override fun visitBooleanValue(ctx: ElaginParser.BooleanValueContext): Value {
        return ValueExpressionVisitor().visitBooleanValue(ctx)

    }

    override fun visitIntegerValue(ctx: ElaginParser.IntegerValueContext): Value {
        return ValueExpressionVisitor().visitIntegerValue(ctx);
    }

    override fun visitMethodCall(ctx: ElaginParser.MethodCallContext): Expression {
        return MethodCallExpressionVisitor(scope, classesContext, this).visitMethodCall(ctx)
    }

    override fun visitSuperCall(ctx: ElaginParser.SuperCallContext): Expression {
        return MethodCallExpressionVisitor(scope, classesContext, this).visitSuperCall(ctx)
    }

    override fun visitConstructorCall(ctx: ElaginParser.ConstructorCallContext): Expression {
        return MethodCallExpressionVisitor(scope, classesContext, this).visitConstructorCall(ctx)
    }


    override fun visitFieldAccessor(ctx: ElaginParser.FieldAccessorContext): Expression {
        return FieldAccessExpressionVisitor(scope, classesContext, this).visitFieldAccessor(ctx)
    }

    override fun visitMultDivExpression(ctx: ElaginParser.MultDivExpressionContext): BinaryExpression {
        return ctx.accept(binaryOperationVisitor)
    }


    override fun visitSumExpression(ctx: ElaginParser.SumExpressionContext): BinaryExpression {
        return ctx.accept(binaryOperationVisitor)
    }

    override fun visitCompareExpression(ctx: ElaginParser.CompareExpressionContext): BinaryExpression {
        return ctx.accept(binaryOperationVisitor)
    }

    override fun visitLogicalExpression(ctx: ElaginParser.LogicalExpressionContext): BinaryExpression {
        return ctx.accept(binaryOperationVisitor)
    }

}