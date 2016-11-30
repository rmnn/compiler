package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Assignment
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.assertCorrectVariableReference
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor
import java.util.*

/**
 * Created by dageev
 * on 10/30/16.
 */
class AssignmentStatementVisitor(scope: Scope, val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Assignment>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Assignment {
        val name = ctx.identifier().text
        val expression = ctx.assignmentExpr.accept(expressionVisitor)
        val classType = if (ctx.classExpr != null) {
            val expr = ctx.classExpr.accept(expressionVisitor)
            if (expr.type is ClassType) {
                assertCorrectVariableReference(classesContext, scope, expr.type, name)
                Optional.of(expr.type)
            } else {
                throw CompilationException("Unable to get field of primitive type for $name")
            }
        } else {
            if (scope.localVariables[name] == null) {
                assertCorrectVariableReference(classesContext, scope, ClassType(scope.className), name)
            }
            Optional.empty()
        }

        return Assignment(classType, name, expression)
    }
}