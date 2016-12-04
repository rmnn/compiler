package ru.dageev.compiler.parser.helper

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.domain.type.PrimitiveType
import ru.dageev.compiler.domain.type.Type
import ru.dageev.compiler.parser.CompilationException
import java.util.*

/**
 * Created by dageev
 * on 11/5/16.
 */


fun assertCorrectVariableReference(classesContext: ClassesContext, scope: Scope, type: ClassType, fieldName: String): Type {
    if (scope.className != type.getTypeName()) {
        val classScope = classesContext.getClassScope(type.getTypeName())
        if (classScope.getFields()[fieldName]!!.accessModifier == AccessModifier.PUBLIC) {
            return classScope.getFields()[fieldName]!!.type
        }
    }

    val (desiredScope, field) = getFieldWithScope(classesContext, scope, type, fieldName, true)
    if (!field.isPresent) {
        throw CompilationException("Field '$fieldName' for '${scope.className}' not exists")
    } else {
        if (field.get().accessModifier == AccessModifier.PRIVATE && desiredScope.className != type.getTypeName()) {
            throw  CompilationException("Unable to get access private field '$fieldName' of class '${desiredScope.className}'")
        }
        return field.get().type
    }
}


fun getField(classesContext: ClassesContext, scope: Scope, type: Type, fieldName: String): Optional<Field> {
    val (desiredScope, field) = getFieldWithScope(classesContext, scope, type, fieldName)
    return field
}

fun getFieldWithScope(classesContext: ClassesContext, scope: Scope, type: Type, fieldName: String, ignoreAccessModifier: Boolean = false): Pair<Scope, Optional<Field>> {
    return if (scope.fieldExists(fieldName)) {
        val field = scope.getFields()[fieldName]!!
        if (scope.className == type.getTypeName() || ignoreAccessModifier || field.accessModifier != AccessModifier.PRIVATE) {
            scope to Optional.of(field)
        } else {
            scope to Optional.empty()
        }
    } else {
        if (scope.parentClassName != null) {
            val parentScope = classesContext.getClassScope(scope.parentClassName)
            getFieldWithScope(classesContext, parentScope, type, fieldName, ignoreAccessModifier)
        } else {
            scope to Optional.empty()
        }
    }
}


fun getDefaultConstructor(scope: Scope): MethodDeclaration.ConstructorDeclaration {
    return MethodDeclaration.ConstructorDeclaration(getDefaultConstructorSignature(scope), Block(scope, emptyList()))
}

fun getDefaultConstructorSignature(scope: Scope): MethodSignature {
    return MethodSignature(AccessModifier.PUBLIC, scope.className, emptyList(), PrimitiveType.VOID)
}


fun getMainMethodSignature() = MethodSignature(AccessModifier.PUBLIC, "main", emptyList(), PrimitiveType.VOID)
