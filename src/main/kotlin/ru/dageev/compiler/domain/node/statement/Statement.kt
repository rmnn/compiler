package ru.dageev.compiler.domain.node.statement

import ru.dageev.compiler.bytecodegeneration.statement.StatementGenerator
import ru.dageev.compiler.domain.node.Node

/**
 * Created by dageev
 *  on 14-May-16.
 */
interface Statement : Node {

    fun accept(generator: StatementGenerator)

}