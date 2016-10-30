package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.VariableDeclaration
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class LocalVariableDeclarationVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<VariableDeclaration>() {
    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): VariableDeclaration {
        val name = ctx.Identifier().text
        val type = getType(ctx.type())
        val expression = ctx.expression().accept(expressionVisitor)
        return VariableDeclaration(name, type, expression)
    }

}