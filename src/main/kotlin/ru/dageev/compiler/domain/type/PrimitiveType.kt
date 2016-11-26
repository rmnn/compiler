package ru.dageev.compiler.domain.type

/**
 * Created by dageev
 *  on 14-May-16.
 */
enum class PrimitiveType(val primitiveTypeName: String, val desc: String) : Type {

    INT("int", "I"),
    BOOLEAN("boolean", "Z"),
    VOID("void", "V");

    override fun getTypeName(): String {
        return primitiveTypeName
    }

    override fun getDescriptor() = desc

    companion object {
        fun getByName(name: String): PrimitiveType {
            return PrimitiveType.values().first { it.primitiveTypeName == name }
        }
    }

}
