package ru.dageev.compiler.parser.visitor.expression

import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ExpressionVisitor(val scope: Scope) : ElaginBaseVisitor<Expression>() {

}