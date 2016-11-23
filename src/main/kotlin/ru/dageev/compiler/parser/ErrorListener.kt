package ru.dageev.compiler.parser

import org.antlr.v4.runtime.BaseErrorListener
import org.antlr.v4.runtime.RecognitionException
import org.antlr.v4.runtime.Recognizer

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ErrorListener : BaseErrorListener() {

    override fun syntaxError(recognizer: Recognizer<*, *>, offendingSymbol: Any, line: Int, charPositionInLine: Int, msg: String?, e: RecognitionException?) {
        throw  CompilationException("You failed at line $line,char $charPositionInLine :(. Details: $msg")
    }
}