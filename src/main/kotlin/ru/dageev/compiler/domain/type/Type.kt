package ru.dageev.compiler.domain.type

/**
 * Created by dageev
 *  on 15-May-16.
 */
interface Type {
    fun getTypeName(): String

    fun getDescriptor(): String

    fun getInternalName(): String

    fun getStoreVariableOpcode(): Int

    fun getReturnOpcode(): Int
}