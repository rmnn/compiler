package ru.dageev.compiler.domain

import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.parser.CompilationException

/**
 * Created by dageev
 * on 10/30/16.
 */
class ClassesContext(val classes: MutableMap<String, ClassDeclaration> = mutableMapOf()) {

    fun addClass(classDeclaration: ClassDeclaration) {
        if (classes.containsKey(classDeclaration.name)) {
            throw  CompilationException("Found duplicated class declaration for class '${classDeclaration.name}'")
        }
        classes.put(classDeclaration.name, classDeclaration)
    }

    fun getClassScope(name: String): Scope {
        val classDeclaration = classes[name] ?: throw CompilationException("Class $name not found")
        return toScope(classDeclaration)
    }

    fun getAllScopes() = classes.values.map { toScope(it) }


    fun toScope(classDecl: ClassDeclaration): Scope {
        return Scope(className = classDecl.name, parentClassName = getParentClassName(classDecl), fields = mutableMapOf(*classDecl.fields.map { it.name to it }.toTypedArray()),
                methodSignatures = classDecl.methods.map { it.methodSignature }.toMutableList(), constructorSignatures = classDecl.constructors.map { it.methodSignature }.toMutableList())
    }

    private fun getParentClassName(classDecl: ClassDeclaration) =
            if (classDecl.parentClassDeclaration.isPresent) classDecl.parentClassDeclaration.get().name
            else null

}