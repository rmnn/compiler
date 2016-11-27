package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 11/26/16.
 */
class ExpressionGenerator(val methodVisitor: MethodVisitor, val scope: Scope) {
    fun generate(expression: Expression) {

    }
}