package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ExpressionVisitor(scope: Scope) : ElaginBaseVisitor<Expression>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

    override fun visitVariableReference(ctx: ElaginParser.VariableReferenceContext): Expression {
        return VariableReferenceVisitor(scope).visitVariableReference(ctx)
    }

    override fun visitValue(ctx: ElaginParser.ValueContext): Expression {
        return ValueExpressionVisitor().visitValue(ctx)
    }


    override fun visitMethodCall(ctx: ElaginParser.MethodCallContext): Expression {
        return visitChildren(ctx)
    }


}