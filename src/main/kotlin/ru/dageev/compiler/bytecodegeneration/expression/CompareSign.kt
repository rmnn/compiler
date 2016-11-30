package ru.dageev.compiler.bytecodegeneration.expression

import org.objectweb.asm.Opcodes

/**
 * Created by dageev
 * on 11/30/16.
 */
enum class CompareSign(val sign: String, val opCode: Int) {
    EQUALS("==", Opcodes.IFEQ),
    NOT_EQUALS("!=", Opcodes.IFNE),
    GREATER(">", Opcodes.IFGT),
    GREATER_EQUALS(">=", Opcodes.IFGE),
    LESS("<", Opcodes.IFLT),
    LESS_EQUALS("<=", Opcodes.IFLE);
}