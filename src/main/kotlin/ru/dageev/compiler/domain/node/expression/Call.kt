package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class Call(val identifier: String, val arguments: List<Argument>, type: Type) : Expression(type) {

    class ConstructorCall(identifier: String, arguments: List<Argument>) : Call(identifier, arguments, ClassType(identifier)) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }

    class MethodCall(val methodSignature: MethodSignature, identifier: String, arguments: List<Argument>, val owner: Expression) : Call(identifier, arguments, methodSignature.returnType) {
        override fun accept(generator: StatementGenerator) {
            generator.generate(this)
        }

        override fun accept(generator: ExpressionGenerator) {
            generator.generate(this)
        }
    }


}