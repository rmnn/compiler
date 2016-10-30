package ru.dageev.compiler.domain.scope


import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.type.Type

/**
 * Created by dageev
 * on 10/30/16.
 */
data class Field(val accessModifier: AccessModifier, val name: String, val type: Type)