package ru.dageev.compiler.domain.type

/**
 * Created by dageev
 *  on 14-May-16.
 */
enum class PrimitiveType(val primitiveTypeName: String) : Type {

    INT("int"),
    BOOLEAN("boolean"),
    VOID("void");

    override fun getTypeName(): String {
        return primitiveTypeName
    }


}
