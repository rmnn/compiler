package ru.dageev.compiler.domain.type

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ClassType(private val classTypeName: String) : Type {
    override fun getTypeName(): String {
        return classTypeName
    }

    override fun toString(): String {
        return "ClassType(classTypeName='$classTypeName')"
    }
}