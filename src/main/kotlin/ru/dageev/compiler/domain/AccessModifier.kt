package ru.dageev.compiler.domain

import jdk.internal.org.objectweb.asm.Opcodes

/**
 * Created by dageev
 *  on 15-May-16.
 */
enum class AccessModifier(val modifierName: String, val opCode: Int) {
    PUBLIC("public", Opcodes.ACC_PUBLIC),
    PRIVATE("private", Opcodes.ACC_PRIVATE)
}