package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.declaration.VariableDeclaration
import ru.dageev.compiler.domain.declaration.VariableDeclaration.LocalVariable
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class LocalVariableDeclarationVisitor(val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<List<LocalVariable>>() {
    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): List<LocalVariable> {
        val type = getType(ctx.type())

        return ctx.localVariableDeclaration().map { variableDeclarator ->
            val name = variableDeclarator.Identifier().text
            val expression = variableDeclarator.expression().accept(expressionVisitor)
            VariableDeclaration.LocalVariable(name, type, expression)
        }
    }

}