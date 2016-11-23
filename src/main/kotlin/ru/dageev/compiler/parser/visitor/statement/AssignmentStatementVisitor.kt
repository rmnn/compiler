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

/**
 * Created by dageev
 * on 10/30/16.
 */
class AssignmentStatementVisitor(val scope: Scope, val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Assignment>() {


    override fun visitAssignment(ctx: ElaginParser.AssignmentContext): Assignment {
        val name = ctx.Identifier().text
        val expression = ctx.assignmentExpr.accept(expressionVisitor)
        val classType = if (ctx.classExpr != null) {
            val expr = ctx.classExpr.accept(expressionVisitor)
            if (expr.type is ClassType) {
                assertCorrectVariableReference(classesContext, scope, expr.type, name)
                expr.type
            } else {
                throw CompilationException("Unable to get field of primitive type for $name")
            }
        } else {
            scope.localVariables[name] ?: throw CompilationException("Field $name for ${scope.className} not exists")
            ClassType(scope.className)
        }

        return Assignment(classType, name, expression)
    }
}