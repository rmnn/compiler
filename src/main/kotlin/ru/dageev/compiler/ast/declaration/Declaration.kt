package ru.dageev.compiler.ast.declaration

import ru.dageev.compiler.ast.AstNode
import ru.dageev.compiler.ast.type.TypeDefinition
import ru.dageev.compiler.ast.visitor.AstNodeVisitor
import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
sealed class Declaration(val identifier: String, override val position: Position) : AstNode {
    override fun accept(visitor: AstNodeVisitor) {
        visitor.visit(this)
    }

    class ClassDeclaration(identifier: String, val members: List<MemberDeclaration>, override val position: Position) : Declaration(identifier, position)

    class ParameterDeclaration(val type: TypeDefinition, val name: String, identifier: String, position: Position) : Declaration(identifier, position)

    sealed class MemberDeclaration(identifier: String, position: Position) : Declaration(identifier, position) {
        class FieldDeclaration(identifier: String, val type: TypeDefinition, val value: String, override val position: Position) : MemberDeclaration(identifier, position)
        class MethodDeclaration(position: Position, identifier: String) : MemberDeclaration(identifier, position)
        class MainMethodDeclaration(position: Position, identifier: String) : MemberDeclaration(identifier, position)
    }
}