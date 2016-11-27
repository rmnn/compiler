package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 * on 11/27/16.
 */
class AssignmentStatementGenerator(val scope: Scope, val methodVisitor: MethodVisitor, val expressionGenerator: ExpressionGenerator) {

    fun generate(assignment: Assignment) {
        if (scope.localVariables.containsKey(assignment.varName)) {
            val index = scope.localVariables.indexOf(assignment.varName)
            val localVariable = scope.localVariables.get(assignment.varName)!!
            val type = assignment.expression.type
            castIfNecessary(type, localVariable.type)
            methodVisitor.visitVarInsn(type.getStoreVariableOpcode(), index)
            return
        } else {
            val field = scope.fields[assignment.varName]!!
            val descriptor = field.type.getDescriptor()
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            assignment.expression.accept(expressionGenerator)
            castIfNecessary(assignment.expression.type, field.type)
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.ownerType.getInternalName(), field.name, descriptor)
        }
    }

    private fun castIfNecessary(expressionType: Type, variableType: Type) {
        if (expressionType != variableType) {
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName())
        }
    }
}