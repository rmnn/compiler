package ru.dageev.compiler.parser.visitor

import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.provider.getAccessModifier

/**
 * Created by dageev
 *  on 15-May-16.
 */
class FieldsVisitor(val typeProvider: TypeProvider, scope: Scope) : ElaginBaseVisitor<Field>() {
    val scope: Scope

    init {
        this.scope = scope.copy()
    }


    override fun visitFieldDeclaration(ctx: ElaginParser.FieldDeclarationContext): Field {
        val type = typeProvider.getType(ctx.type())
        val accessModifier = getAccessModifier(ctx.accessModifier())
        val name = ctx.Identifier().text
        return Field(accessModifier, name, type, ClassType(scope.className))
    }
}
