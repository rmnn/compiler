package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.declaration.VariableDeclaration
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class FieldsVisitor(val scope: Scope) : ElaginBaseVisitor<List<VariableDeclaration.Field>>() {

    override fun visitFieldDeclaration(ctx: ElaginParser.FieldDeclarationContext): List<VariableDeclaration.Field> {
        val type = getType(ctx.type())
        val accessModifier = getAccessModifier(ctx.accessModifier())
        return ctx.variableDeclarators().variableDeclarator().map { variableDeclarator ->
            val name = variableDeclarator.Identifier().text
            val expression = variableDeclarator.expression().accept(ExpressionVisitor(scope))
            VariableDeclaration.Field(accessModifier, name, type, expression)
        }
    }
}