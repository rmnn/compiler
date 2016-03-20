package ru.dageev.compiler.lexer.token

import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/20/2016.
 */
data class Token(val type: TokenType, val value: String, val position: Position)