package ru.dageev.compiler.ast

import ru.dageev.compiler.ast.declaration.Declaration.ClassDeclaration
import ru.dageev.compiler.ast.visitor.AstNodeVisitor
import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
class Program(val classDeclarations: List<ClassDeclaration>, override val position: Position) : AstNode {
    override fun accept(visitor: AstNodeVisitor) {
        visitor.visit(this)
    }
}