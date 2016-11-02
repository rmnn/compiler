package ru.dageev.compiler.domain.node.expression

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class BinaryExpression(val leftExpression: Expression, val rightExpression: Expression) : Expression(leftExpression.type) {
    class AdditionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class DivisionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class EqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class GreaterExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class GreaterEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class LessExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class LessEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class LogicalOrExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class LogicalAndExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class ModuleExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class MultiplicationExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class NonEqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)

    class SubtractionExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression)
}