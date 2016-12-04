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


    override fun toString(): String {
        return "ClassType(classTypeName='$classTypeName')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ClassType

        if (classTypeName != other.classTypeName) return false

        return true
    }

    override fun hashCode(): Int {
        return classTypeName.hashCode()
    }
}