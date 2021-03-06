package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class VariableReference(type: Type) : Expression(type) {

    class LocalVariableReference(val localVariable: LocalVariable) : VariableReference(localVariable.type) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class FieldReference(val field: Field) : VariableReference(field.type) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }


}