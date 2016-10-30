package ru.dageev.compiler.domain

import ru.dageev.compiler.domain.declaration.ClassDeclaration

/**
 * Created by dageev
 * on 10/30/16.
 */
class ClassesContext(val classes: MutableMap<String, ClassDeclaration> = mutableMapOf()) {
    fun addClass(classDeclaration: ClassDeclaration) {
        classes.put(classDeclaration.name, classDeclaration)
    }
}