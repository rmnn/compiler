package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class BinaryExpression(val leftExpression: Expression, val rightExpression: Expression, type: Type) : Expression(type) {

    class AdditionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class DivisionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class EqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class ModuleExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class MultiplicationExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class SubtractionExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT)

    class GreaterExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class GreaterEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class LessEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class LogicalOrExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class LogicalAndExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class NonEqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)

    class LessExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN)
}