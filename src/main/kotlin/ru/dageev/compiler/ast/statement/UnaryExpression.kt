package ru.dageev.compiler.ast.statement

import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
sealed class UnaryExpression(val expression: Expression, position: Position) : Expression(position) {

    class VariableAccessExpression(expression: Expression, position: Position) : UnaryExpression(expression, position)

    class ReturnStatement(expression: Expression, position: Position) : UnaryExpression(expression, position)

    class MethodInvocationExpression(val method: String, expression: Expression, val parameters: List<Expression>, position: Position) : UnaryExpression(expression, position)

    sealed class PrimaryExpression(expression: Expression, position: Position) : UnaryExpression(expression, position) {

        class IntegerLiteralExpression(val literal: String, val negative: Boolean, expression: Expression, position: Position) : PrimaryExpression(expression, position)

        class BooleanLiteralExpression(val value: Boolean, expression: Expression, position: Position) : PrimaryExpression(expression, position)

        class NullLiteralExpression(expression: Expression, position: Position) : PrimaryExpression(expression, position)

        class NewObjectExpression(val name: String, expression: Expression, position: Position) : PrimaryExpression(expression, position)
    }
}