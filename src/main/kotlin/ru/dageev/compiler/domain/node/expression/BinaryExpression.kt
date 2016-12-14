package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class BinaryExpression(val leftExpression: Expression, val rightExpression: Expression, type: Type) : Expression(type) {

    abstract fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression


    class AdditionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {
        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return AdditionalExpression(leftExpression, rightExpression)
        }

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class DivisionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return DivisionalExpression(leftExpression, rightExpression)
        }
    }

    class ModuleExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }
        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return ModuleExpression(leftExpression, rightExpression)
        }

    }

    class MultiplicationExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return MultiplicationExpression(leftExpression, rightExpression)
        }
    }

    class SubtractionExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return SubtractionExpression(leftExpression, rightExpression)
        }
    }

    class EqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return EqualityExpression(leftExpression, rightExpression)
        }
    }

    class GreaterExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return GreaterExpression(leftExpression, rightExpression)
        }
    }

    class GreaterEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return GreaterEqualsExpression(leftExpression, rightExpression)
        }
    }

    class LessEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return LessEqualsExpression(leftExpression, rightExpression)
        }
    }

    class LogicalOrExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return LogicalOrExpression(leftExpression, rightExpression)
        }
    }

    class LogicalAndExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return LogicalAndExpression(leftExpression, rightExpression)
        }
    }

    class NonEqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return NonEqualityExpression(leftExpression, rightExpression)
        }
    }

    class LessExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

        override fun copy(leftExpression: Expression, rightExpression: Expression): BinaryExpression {
            return LessExpression(leftExpression, rightExpression)
        }
    }


}