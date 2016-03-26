package ru.dageev.compiler.ast

import ru.dageev.compiler.ast.visitor.AstNodeVisitor
import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
interface AstNode {
    val position: Position
    fun accept(visitor: AstNodeVisitor)
}