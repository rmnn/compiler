package ru.dageev.domain.declaration

import ru.dageev.domain.AccessModifier
import ru.dageev.domain.node.statement.Statement
import ru.dageev.domain.scope.MethodSignature

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class MethodDeclaration(val accessModifier: AccessModifier, val methodSignature: MethodSignature, val statement: Statement) {
    class ConstructorDeclaration(accessModifier: AccessModifier, methodSignature: MethodSignature, statement: Statement) :
            MethodDeclaration(accessModifier, methodSignature, statement)
}