package ru.dageev.compiler.domain.type

import org.objectweb.asm.Opcodes

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ClassType(private val classTypeName: String) : Type {

    override fun getTypeName(): String {
        return classTypeName
    }

    override fun getDescriptor() = "L" + getInternalName() + ";"

    override fun getInternalName() = classTypeName.replace(".", "/")

    override fun getStoreVariableOpcode() = Opcodes.ASTORE

    override fun getReturnOpcode() = Opcodes.ARETURN

    override fun getLoadVariableOpcode() = Opcodes.ALOAD


    fun getAddOpcode(): Nothing = throw RuntimeException("Addition is not supported for")


    fun getSubstractOpcode(): Nothing = throw RuntimeException("Substraction operation not (yet ;) ) supported for custom objects")

    fun getMultiplyOpcode(): Nothing = throw RuntimeException("Multiplcation operation not (yet ;) ) supported for custom objects")


    fun getDividOpcode(): Nothing = throw RuntimeException("Division operation not (yet ;) ) supported for custom objects")


    override fun toString(): String {
        return "ClassType(classTypeName='$classTypeName')"
    }
}