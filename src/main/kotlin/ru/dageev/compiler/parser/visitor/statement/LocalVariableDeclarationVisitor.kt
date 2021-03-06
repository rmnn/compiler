package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.node.statement.VariableDeclaration
import ru.dageev.compiler.domain.scope.LocalVariable
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.provider.TypeProvider
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class LocalVariableDeclarationVisitor(val scope: Scope, val typeProvider: TypeProvider, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<VariableDeclaration>() {
    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): VariableDeclaration {
        val name = ctx.identifier().text
        val type = typeProvider.getType(ctx.type())
        if (type == PrimitiveType.VOID && ctx.type() != null) {
            throw CompilationException("Variable '$name' could not have VOID type")
        }
        val expression = ctx.expression().accept(expressionVisitor)
        if (ctx.type() == null && expression.type == PrimitiveType.VOID) {
            throw CompilationException("Variable '$name' could not have VOID type")
        }
        val variableType = if (ctx.type() == null) expression.type else type
        scope.addLocalVariable(LocalVariable(name, variableType))
        return VariableDeclaration(name, variableType, expression)
    }

}