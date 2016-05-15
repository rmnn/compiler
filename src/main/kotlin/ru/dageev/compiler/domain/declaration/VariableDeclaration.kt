package ru.dageev.compiler.domain.declaration

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.node.expression.Expression
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 14-May-16.
 */
sealed class VariableDeclaration(val name: String, val type: Type, val expression: Expression) {

    class LocalVariable(name: String, type: Type, expression: Expression) : VariableDeclaration(name, type, expression)

    class Field(val accessModifier: AccessModifier, name: String, type: Type, expression: Expression) : VariableDeclaration(name, type, expression)
}