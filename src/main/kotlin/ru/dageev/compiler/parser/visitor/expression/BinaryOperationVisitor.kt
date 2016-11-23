package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.BinaryExpression
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 * on 11/2/16.
 */
class BinaryOperationVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<BinaryExpression>() {

    override fun visitMultDivExpression(ctx: ElaginParser.MultDivExpressionContext): BinaryExpression {
        val left = ctx.expression(0).accept(expressionVisitor)
        val right = ctx.expression(1).accept(expressionVisitor)
        return when (ctx.operation.text) {
            "*" -> BinaryExpression.MultiplicationExpression(left, right)
            "/" -> BinaryExpression.DivisionalExpression(left, right)
            "%" -> BinaryExpression.ModuleExpression(left, right)
            else -> throw CompilationException("Wrong operation multiDiv operation ${ctx.operation.text}")
        }
    }


    override fun visitSumExpression(ctx: ElaginParser.SumExpressionContext): BinaryExpression {
        val left = ctx.expression(0).accept(expressionVisitor)
        val right = ctx.expression(1).accept(expressionVisitor)
        return when (ctx.operation.text) {
            "+" -> BinaryExpression.AdditionalExpression(left, right)
            "-" -> BinaryExpression.SubtractionExpression(left, right)
            else -> throw CompilationException("Wrong operation ${ctx.operation.text}")
        }
    }

    override fun visitCompareExpression(ctx: ElaginParser.CompareExpressionContext): BinaryExpression {
        val left = ctx.expression(0).accept(expressionVisitor)
        val right = ctx.expression(1).accept(expressionVisitor)
        return when (ctx.operation.text) {
            "<" -> BinaryExpression.LessExpression(left, right)
            "<=" -> BinaryExpression.LessEqualsExpression(left, right)
            ">" -> BinaryExpression.GreaterExpression(left, right)
            ">=" -> BinaryExpression.GreaterEqualsExpression(left, right)
            "==" -> BinaryExpression.EqualityExpression(left, right)
            "!=" -> BinaryExpression.NonEqualityExpression(left, right)
            else -> throw CompilationException("Wrong operation ${ctx.operation.text}")
        }
    }

    override fun visitLogicalExpression(ctx: ElaginParser.LogicalExpressionContext): BinaryExpression {
        val left = ctx.expression(0).accept(expressionVisitor)
        val right = ctx.expression(1).accept(expressionVisitor)
        return when (ctx.operation.text) {
            "&&" -> BinaryExpression.LogicalAndExpression(left, right)
            "||" -> BinaryExpression.LogicalOrExpression(left, right)
            else -> throw CompilationException("Wrong operation ${ctx.operation.text}")
        }
    }


}

