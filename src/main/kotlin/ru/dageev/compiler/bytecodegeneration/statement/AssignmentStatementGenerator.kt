package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import ru.dageev.compiler.bytecodegeneration.expression.ExpressionGenerator
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.getField

/**
 * Created by dageev
 * on 11/27/16.
 */
class AssignmentStatementGenerator(val scope: Scope, val classesContext: ClassesContext, val methodVisitor: MethodVisitor, val expressionGenerator: ExpressionGenerator) {

    fun generate(assignment: Assignment) {
        if (scope.localVariables.containsKey(assignment.varName) && !assignment.classType.isPresent) {
            val index = scope.localVariables.indexOf(assignment.varName)
            val localVariable = scope.localVariables[assignment.varName]!!
            val type = assignment.expression.type
            castIfNecessary(type, localVariable.type)
            methodVisitor.visitVarInsn(type.getStoreVariableOpcode(), index)
            return
        } else {
            val fieldOptional = getField(classesContext, scope, assignment.classType.orElseGet { ClassType(scope.className) }, assignment.varName)
            if (!fieldOptional.isPresent) {
                throw CompilationException("Unable to find variable for assignment $assignment")
            }
            val field = fieldOptional.get()
            val descriptor = field.type.getDescriptor()
            methodVisitor.visitVarInsn(Opcodes.ALOAD, 0)
            assignment.expression.accept(expressionGenerator)
            castIfNecessary(assignment.expression.type, field.type)
            methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.ownerType.getInternalName(), field.name, descriptor)
        }
    }

    private fun castIfNecessary(expressionType: Type, variableType: Type) {
        if (expressionType.getTypeName() != variableType.getTypeName()) {
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName())
        }
    }
}