package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 * on 10/30/16.
 */
class VariableReferenceVisitor(scope: Scope) : ElaginBaseVisitor<VariableReference>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitVariableReference(ctx: ElaginParser.VariableReferenceContext): VariableReference {
        val name = ctx.text
        return if (fieldExists(name)) {
            VariableReference.FieldReference(scope.fields[name]!!)
        } else {
            // todo exception if local var not found
            val variable = scope.localVariables[name]!!
            return VariableReference.LocalVariableReference(variable)
        }
    }

    fun fieldExists(name: String) = scope.fields.any { it.key == name }
}