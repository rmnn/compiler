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
            val ownerDescriptor = ClassType(signature.get().name).getInternalName()
            methodVisitor.visitTypeInsn(Opcodes.NEW, ownerDescriptor)
            methodVisitor.visitInsn(Opcodes.DUP)
            val methodDescriptor = signature.get().getDescriptor()
            generateArguments(constructorCall)
            methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false)
        }
    }


    fun generate(methodCall: Call.MethodCall, forMainClass: Boolean) {
        methodCall.owner.accept(expressionGenerator)
        generateArguments(methodCall)
        val methodDescriptor = methodCall.methodSignature.getDescriptor()
        val ownerDescriptor = methodCall.owner.type.getInternalName()
        val invokeCode = if (methodCall.owner.type == ClassType("ElaginProgram")) Opcodes.INVOKESTATIC else Opcodes.INVOKEVIRTUAL
        methodVisitor.visitMethodInsn(invokeCode, ownerDescriptor, methodCall.methodSignature.name, methodDescriptor, false)
    }

    fun generate(superCall: Call.SuperCall) {
        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
        generateArguments(superCall)
        val ownerDescriptor = superCall.type.getInternalName()
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "()V", false)
    }

    private fun getConstructor(constructorCallExpression: Call.ConstructorCall): Optional<MethodSignature> {
        return if (scope.className == constructorCallExpression.type.getTypeName()) {
            scope.getConstructorCallSignature(constructorCallExpression.arguments)
        } else {
            val requiredClassScope = classesContext.getClassScope(constructorCallExpression.type.getTypeName())
            requiredClassScope.getConstructorCallSignature(constructorCallExpression.arguments)
        }
    }


    private fun generateArguments(call: Call) =
            call.arguments.forEach { argument -> argument.accept(expressionGenerator) }


}