package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class Call(val identifier: String, val arguments: List<Argument>, type: Type) : Expression(type) {

    class ConstructorCall(identifier: String, arguments: List<Argument>) : Call(identifier, arguments, ClassType(identifier))

    class MethodCall(val methodSignature: MethodSignature, identifier: String, arguments: List<Argument>, val owner: Expression) : Call(identifier, arguments, methodSignature.returnType)
}