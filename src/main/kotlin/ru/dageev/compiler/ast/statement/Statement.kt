package ru.dageev.compiler.ast.statement

import ru.dageev.compiler.ast.AstNode
import ru.dageev.compiler.ast.visitor.AstNodeVisitor
import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
open class Statement(override val position: Position) : AstNode {
    override fun accept(visitor: AstNodeVisitor) {
        visitor.visit(this)
    }
}