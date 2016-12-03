package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.FieldAccess
import ru.dageev.compiler.domain.node.expression.VariableReference
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.assertCorrectVariableReference

/**
 * Created by dageev
 * on 11/2/16.
 */
class FieldAccessExpressionVisitor(scope: Scope, val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<FieldAccess>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitFieldAccessor(ctx: ElaginParser.FieldAccessorContext): FieldAccess {
        val name = ctx.identifier().text
        val expression = ctx.expression().accept(expressionVisitor)
        if (expression.type is ClassType) {
            if (expression !is VariableReference) {
                throw CompilationException("Unable to get field for not variable expression")
            }
            val fieldType = assertCorrectVariableReference(classesContext, scope, expression.type, name)
            return FieldAccess(name, fieldType, expression)
        } else {
            throw CompilationException("Unable to access field of primitive types")
        }
    }
}