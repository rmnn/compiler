package ru.dageev.compiler.bytecodegeneration.statement

import org.objectweb.asm.MethodVisitor
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.node.statement.Block

/**
 * Created by dageev
 * on 11/27/16.
 */
class BlockStatementGenerator(val classesContext: ClassesContext, val methodVisitor: MethodVisitor) {

    fun generate(block: Block) {
        val statementGenerator = StatementGenerator(block.scope, classesContext, methodVisitor)
        block.statements.forEach { it.accept(statementGenerator) }
    }
}