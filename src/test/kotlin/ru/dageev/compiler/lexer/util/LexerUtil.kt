package ru.dageev.compiler.lexer.util

import ru.dageev.compiler.lexer.Lexer
import java.io.StringReader

/**
 * Created by dageev
 *  on 03/20/2016.
 */
fun getLexer(program: String) = Lexer(StringReader(program))