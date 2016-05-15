package ru.dageev.compiler.parser.provider

import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
fun getType(typeContext: ElaginParser.TypeContext?): Type {
    return if (typeContext == null) {
        PrimitiveType.VOID
    } else {
        getByName(typeContext.text)
    }

}

private fun getByName(text: String): Type {
    return if (primitiveType(text)) {
        PrimitiveType.valueOf(text)
    } else {
        ClassType(text)
    }
}

private fun primitiveType(text: String): Boolean {
    return PrimitiveType.values().any { it.getTypeName() == text }
}
