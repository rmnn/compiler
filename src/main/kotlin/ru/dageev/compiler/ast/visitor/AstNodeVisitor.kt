package ru.dageev.compiler.ast.visitor

import ru.dageev.compiler.ast.AstNode

/**
 * Created by dageev
 *  on 03/26/2016.
 */
interface AstNodeVisitor {
    fun visit(node: AstNode)
}