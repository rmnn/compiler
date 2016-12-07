package ru.dageev.compiler.domain.type

import jdk.internal.org.objectweb.asm.Opcodes

/**
 * Created by dageev
 *  on 14-May-16.
 */
enum class PrimitiveType(val primitiveTypeName: String, val desc: String, val storeCode: Int, val returnCode: Int, val loadCode: Int) : Type {

    INT("int", "I", Opcodes.ISTORE, Opcodes.IRETURN, Opcodes.ILOAD),
    BOOLEAN("boolean", "Z", Opcodes.ISTORE, Opcodes.IRETURN, Opcodes.ILOAD),
    VOID("void", "V", Opcodes.ASTORE, Opcodes.RETURN, Opcodes.ALOAD);

    override fun getTypeName(): String {
        return primitiveTypeName
    }

    override fun getDescriptor() = desc

    override fun getInternalName() = desc

    override fun getStoreVariableOpcode() = storeCode

    override fun getReturnOpcode() = returnCode

    override fun getLoadVariableOpcode() = loadCode

    companion object {
        fun getByName(name: String): PrimitiveType {
            return PrimitiveType.values().first { it.primitiveTypeName == name }
        }
    }

}
