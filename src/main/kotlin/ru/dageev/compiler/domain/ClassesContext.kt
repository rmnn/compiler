package ru.dageev.compiler.domain

import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.scope.Scope

/**
 * Created by dageev
 * on 10/30/16.
 */
class ClassesContext(val classes: MutableMap<String, ClassDeclaration> = mutableMapOf()) {

    fun addClass(classDeclaration: ClassDeclaration) {
        classes.put(classDeclaration.name, classDeclaration)
    }

    // TODO error handle
    fun getClassDeclaration(name: String): ClassDeclaration {
        return classes[name]!!
    }

    fun getClassScope(name: String): Scope {
        return toScope(classes[name]!!)
    }

    fun toScope(classDecl: ClassDeclaration): Scope {
        val methods = classDecl.methods + classDecl.constructors
        return Scope(className = classDecl.name, fields = mutableMapOf(*classDecl.fields.map { it.name to it }.toTypedArray()),
                methodSignatures = methods.map { it.methodSignature }.toMutableList())
    }
}