package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.declaration.VariableDeclaration
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.getAccessModifier
import ru.dageev.compiler.parser.provider.getType

/**
 * Created by dageev
 *  on 15-May-16.
 */
class FieldsVisitor(val scope: Scope) : ElaginBaseVisitor<VariableDeclaration.Field>() {

    override fun visitFieldDeclaration(ctx: ElaginParser.FieldDeclarationContext): VariableDeclaration.Field {
        val type = getType(ctx.type())
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val name = ctx.Identifier().text
        return VariableDeclaration.Field(accessModifier, name, type)
    }
}
