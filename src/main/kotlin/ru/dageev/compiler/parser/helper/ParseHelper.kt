package ru.dageev.compiler.parser.helper

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 * on 11/5/16.
 */
fun assertCorrectVariableReference(classesContext: ClassesContext, scope: Scope, type: ClassType, name: String) {
    if (!hasParent(classesContext, scope, type.classTypeName)) {
        throw CompilationException("Field $name for ${scope.className} not exists")
    } else {
        if (scope.fields[name]!!.accessModifier == AccessModifier.PRIVATE) {
            throw  CompilationException("Unable to assign parent class '${type.classTypeName}' private field $name")
        }
    }
}


private fun hasParent(classesContext: ClassesContext, scope: Scope, name: String): Boolean {
    return scope.className == name || (scope.parentClassName != null && hasParent(classesContext, classesContext.getClassScope(scope.parentClassName), name))
}