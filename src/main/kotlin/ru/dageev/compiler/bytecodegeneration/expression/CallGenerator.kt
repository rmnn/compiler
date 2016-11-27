package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.Call
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.parser.CompilationException
import java.util.*

/**
 * Created by dageev
 * on 11/27/16.
 */
class CallGenerator(val scope: Scope, val classesContext: ClassesContext, val expressionGenerator: ExpressionGenerator, val methodVisitor: MethodVisitor) {

    fun generate(constructorCall: Call.ConstructorCall) {
        val signature = getConstructor(constructorCall)
        if (!signature.isPresent) {
            throw CompilationException("Unable to find constructor for $constructorCall")
        } else {
            val ownerDescriptor = ClassType(signature.get().name).getDescriptor()
            methodVisitor.visitTypeInsn(Opcodes.NEW, ownerDescriptor)
            methodVisitor.visitInsn(Opcodes.DUP)
            val methodDescriptor = signature.get().getDescriptor()
            generateArguments(constructorCall, signature.get())
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false)
        }
    }


    fun generate(methodCall: Call.MethodCall) {
        methodCall.owner.accept(expressionGenerator)
        generateArguments(methodCall, methodCall.methodSignature)
        val methodDescriptor = methodCall.methodSignature.getDescriptor()
        val ownerDescriptor = methodCall.owner.type.getInternalName()
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ownerDescriptor, methodCall.methodSignature.name, methodDescriptor, false)
    }

    private fun getConstructor(constructorCallExpression: Call.ConstructorCall): Optional<MethodSignature> {
        return if (scope.className == constructorCallExpression.type.getTypeName()) {
            scope.getConstructorCallSignature(constructorCallExpression.arguments)
        } else {
            val requiredClassScope = classesContext.getClassScope(constructorCallExpression.type.getTypeName())
            requiredClassScope.getConstructorCallSignature(constructorCallExpression.arguments)
        }
    }


    private fun generateArguments(call: Call, signature: MethodSignature) {
        if (call.arguments.size > signature.parameters.size) {
            throw CompilationException("Too much arguments found for call $call")
        }
        call.arguments.forEach { argument -> argument.accept(expressionGenerator) }
    }

}