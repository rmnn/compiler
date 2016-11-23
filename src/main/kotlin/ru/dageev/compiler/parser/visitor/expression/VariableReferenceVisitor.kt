package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.getField

/**
 * Created by dageev
 * on 10/30/16.
 */
class VariableReferenceVisitor(scope: Scope, val classesContext: ClassesContext) : ElaginBaseVisitor<VariableReference>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitVariableReference(ctx: ElaginParser.VariableReferenceContext): VariableReference {
        val name = ctx.text
        val field = getField(classesContext, scope, ClassType(scope.className), name)
        return if (field.isPresent) {
            VariableReference.FieldReference(field.get())
        } else {
            val localVariable = scope.localVariables[name]
            if (localVariable == null) {
                throw CompilationException("Local variable '$name' not found for class '${scope.className}'")
            } else {
                return VariableReference.LocalVariableReference(localVariable)
            }
        }
    }
}