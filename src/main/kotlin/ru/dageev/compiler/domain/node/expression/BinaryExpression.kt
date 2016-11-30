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


    class AdditionalExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {
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
    }

    class ModuleExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }
        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

    }

    class MultiplicationExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

    }

    class SubtractionExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {

        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }

    }

    class EqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.INT) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class GreaterExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class GreaterEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class LessEqualsExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class LogicalOrExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class LogicalAndExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class NonEqualityExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class LessExpression(leftExpression: Expression, rightExpression: Expression) : BinaryExpression(leftExpression, rightExpression, PrimitiveType.BOOLEAN) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }


}