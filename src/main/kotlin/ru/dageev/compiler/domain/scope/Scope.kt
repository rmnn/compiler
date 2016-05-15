package ru.dageev.compiler.domain.scope

import ru.dageev.compiler.domain.declaration.VariableDeclaration

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Scope(val methodSignatures: List<MethodSignature>, localVariables: Map<String, VariableDeclaration.LocalVariable>, fields: Map<String, VariableDeclaration.Field>)