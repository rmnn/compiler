package ru.dageev.compiler.parser

import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.grammar.ElaginLexer
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.visitor.CompilationUnitVisitor

/**
 * Created by dageev
 *  on 15-May-16.
 */
class Parser {

    fun parse(path: String): CompilationUnit? {
        val parser = getElaginParser(path)
        parser.addErrorListener(ErrorListener())
        val compilationUnitVisitor = CompilationUnitVisitor()
        return parser.compilationUnit().accept(compilationUnitVisitor)
    }

    private fun getElaginParser(path: String): ElaginParser {
        val charStream = ANTLRFileStream(path)
        val lexer = ElaginLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = ElaginParser(tokenStream)
        return parser
    }
}
