package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.node.expression.Value
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 * on 11/27/16.
 */
class ValueExpressionGenerator(val methodVisitor: MethodVisitor) {

    fun generate(value: Value) {
        when (value.type) {
            PrimitiveType.INT -> methodVisitor.visitLdcInsn(value.value.toInt())
            PrimitiveType.BOOLEAN -> methodVisitor.visitLdcInsn(value.value.toBoolean())
            else -> throw CompilationException("Unsupported value type ${value.type}")
        }
    }
}