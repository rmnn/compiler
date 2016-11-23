package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import java.util.*

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
        val field = getField(scope.className, scope, name)
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

    fun getField(childClass: String, scope: Scope, fieldName: String): Optional<Field> {
        return if (scope.fieldExists(fieldName)) {
            val field = scope.fields[fieldName]!!
            if (scope.className != childClass && field.accessModifier == AccessModifier.PRIVATE) {
                Optional.empty()
            } else {
                Optional.of(field)
            }
        } else {
            if (scope.parentClassName != null) {
                val parentScope = classesContext.getClassScope(scope.parentClassName)
                getField(childClass, parentScope, fieldName)
            } else {
                Optional.empty()
            }
        }
    }
}