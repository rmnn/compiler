package ru.dageev.compiler.parser

import org.antlr.v4.runtime.ANTLRFileStream
import ru.dageev.compiler.domain.CompilationUnit

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Parser {

    fun parse(path: String): CompilationUnit? {
        val charStream = ANTLRFileStream(path)
//        val lexer = ElaginLexer(charStream)  //create lexer (pass enk file to it)
//        val tokenStream = CommonTokenStream(lexer)
//        val parser = EnkelParser(tokenStream)
        return null

    }
}