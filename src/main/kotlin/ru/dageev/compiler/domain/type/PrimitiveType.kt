package ru.dageev.compiler.domain.type

import org.objectweb.asm.Opcodes

/**
 * Created by dageev
 *  on 14-May-16.
 */
enum class PrimitiveType(val primitiveTypeName: String, val desc: String, val storeCode: Int, val returnCode: Int) : Type {

    INT("int", "I", Opcodes.ISTORE, Opcodes.IRETURN),
    BOOLEAN("boolean", "Z", Opcodes.ISTORE, Opcodes.IRETURN),
    VOID("void", "V", Opcodes.ASTORE, Opcodes.RETURN);

    override fun getTypeName(): String {
        return primitiveTypeName
    }

    override fun getDescriptor() = desc

    override fun getInternalName() = desc

    override fun getStoreVariableOpcode() = storeCode

    override fun getReturnOpcode() = returnCode


    companion object {
        fun getByName(name: String): PrimitiveType {
            return PrimitiveType.values().first { it.primitiveTypeName == name }
        }
    }

}
