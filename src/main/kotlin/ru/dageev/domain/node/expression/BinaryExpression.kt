package ru.dageev.domain.node.expression

import ru.dageev.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class BinaryExpression(val leftExpression: Expression, val rightExpression: Expression, type: Type) : Expression(type) {
    class AssignmentExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class AdditionalExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class DivisionalExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class EqualityExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class GreaterExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class GreaterEqualsExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class LessExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class LessEqualsExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)
    class LogicalOrExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class LogicalAndExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class ModuleExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class MultiplicationExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class NonEqualityExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)

    class SubtractionExpression(leftExpression: Expression, rightExpression: Expression, type: Type) : BinaryExpression(leftExpression, rightExpression, type)
}