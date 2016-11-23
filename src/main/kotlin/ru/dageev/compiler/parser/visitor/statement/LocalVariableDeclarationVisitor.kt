package ru.dageev.compiler.parser.visitor.statement

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.VariableDeclaration
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.provider.getType
import ru.dageev.compiler.parser.visitor.expression.ExpressionVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class LocalVariableDeclarationVisitor(val classesContext: ClassesContext, val expressionVisitor: ExpressionVisitor) : ElaginBaseVisitor<VariableDeclaration>() {
    override fun visitLocalVariableDeclarationStatement(ctx: ElaginParser.LocalVariableDeclarationStatementContext): VariableDeclaration {
        val name = ctx.Identifier().text
        val type = getType(classesContext, ctx.type())
        if (type == PrimitiveType.VOID) {
            throw CompilationException("Variable $name could not have VOID type")
        }
        val expression = ctx.expression().accept(expressionVisitor)
        return VariableDeclaration(name, type, expression)
    }

}