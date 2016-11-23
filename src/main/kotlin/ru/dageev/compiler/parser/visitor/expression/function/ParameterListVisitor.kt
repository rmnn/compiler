package ru.dageev.compiler.parser.visitor.expression.function

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.expression.Parameter
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ParameterListVisitor(val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<List<Parameter>>() {
    override fun visitFormalParameters(ctx: ElaginParser.FormalParametersContext): List<Parameter> {
        val formalParameterList = ctx.formalParameterList() ?: return emptyList()
        return formalParameterList.formalParameter().map { it.accept(ParameterVisitor(classesContext, expressionVisitor)) }
    }
}