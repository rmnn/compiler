package ru.dageev.compiler.domain.node.expression

import ru.dageev.compiler.domain.declaration.VariableDeclaration
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 *  on 15-May-16.
 */
sealed class Reference(val name: String, type: Type) : Expression(type) {

    class LocalVariableReference(val localVariable: VariableDeclaration.LocalVariable, name: String, type: Type) : Reference(name, type)

    class FieldReference(val field: VariableDeclaration.Field, name: String, type: Type) : Reference(name, type)
}