package ru.dageev.compiler.parser.provider

import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
fun getAccessModifier(accessModifierContext: ElaginParser.AccessModifierContext?): AccessModifier {
    return if (accessModifierContext == null) {
        AccessModifier.PUBLIC
    } else {
        AccessModifier.values().find { it.modifierName == accessModifierContext.text }!!
    }
}
