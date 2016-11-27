package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.node.statement.Block

/**
 * Created by dageev
 * on 11/27/16.
 */
class BlockStatementGenerator(val methodVisitor: MethodVisitor) {

    fun generate(block: Block) {
        val statementGenerator = StatementGenerator(methodVisitor, block.scope)
        block.statements.forEach { it.accept(statementGenerator) }
    }
}