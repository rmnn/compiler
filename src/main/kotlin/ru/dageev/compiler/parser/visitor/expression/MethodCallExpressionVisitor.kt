package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.Call
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor

/**
 * Created by dageev
 * on 10/30/16.
 */
class MethodCallExpressionVisitor(scope: Scope, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<Call>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }

//    override fun visitMethodCall(ctx: ElaginParser.MethodCallContext): Call {
//        val functionName = ctx.Identifier().text
//        if (functionName == scope.className) {
//            throw IllegalArgumentException(functionName)
//        }
//
//        val arguments = getArgumentsForCall(ctx.expressionList())
//        val classIsExplicit = ctx.classRef
//
//    }
//
//    private fun getArgumentsForCall(expressionList: ElaginParser.ExpressionListContext?): List<Argument> {
//        if (expressionList == null) {
//            return emptyList()
//        }
//
//        return expressionList.expression().map { Argument(it.accept(expressionVisitor)) }
//    }
}