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


fun getTypeFromValue(value: ElaginParser.ValueContext): Type {
    val stringValue = value.text
    if (stringValue == null || stringValue.isEmpty()) {
        return PrimitiveType.VOID
    }

    if (value.BooleanLiteral() != null) {
        return PrimitiveType.BOOLEAN
    }
    return PrimitiveType.INT

}

private fun getByName(text: String): Type {
    return if (primitiveType(text)) {
        PrimitiveType.getByName(text)
    } else {
        ClassType(text)
    }
}

private fun primitiveType(text: String): Boolean {
    return PrimitiveType.values().any { it.getTypeName() == text }
}
