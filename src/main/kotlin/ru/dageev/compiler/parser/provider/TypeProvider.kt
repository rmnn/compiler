package ru.dageev.compiler.parser.provider

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 *  on 15-May-16.
 */
fun getType(classesContext: ClassesContext, typeContext: ElaginParser.TypeContext?): Type {
    return if (typeContext == null) {
        PrimitiveType.VOID
    } else {
        getByName(classesContext, typeContext.text)
    }

}


private fun getByName(classesContext: ClassesContext, text: String): Type {
    return if (primitiveType(text)) {
        PrimitiveType.getByName(text)
    } else {
        if (classesContext.classes.containsKey(text)) {
            ClassType(text)
        } else {
            throw CompilationException("Class $text not exists")
        }
    }
}

private fun primitiveType(text: String): Boolean {
    return PrimitiveType.values().any { it.getTypeName() == text }
}
