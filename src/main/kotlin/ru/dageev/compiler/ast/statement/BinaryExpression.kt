package ru.dageev.compiler.ast.statement

import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
sealed class BinaryExpression(val operand1: Expression, val operand2: Expression, position: Position) : Expression(position) {

    class AssignmentExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class AdditionalExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class DivisionalExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class EqualityExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class GreaterExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class GreaterEqualsExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class LessExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class LessEqualsExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class LogicalOrExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class LogicalAndExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class ModuleExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class MultiplicationExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class NonEqualityExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)

    class SubtractionExpression(operand1: Expression, operand2: Expression, position: Position) : BinaryExpression(operand1, operand2, position)
}