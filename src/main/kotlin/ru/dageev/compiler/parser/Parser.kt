package ru.dageev.compiler.parser

import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CharStream
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

    fun parse(path: String): CompilationUnit {
        val charStream = ANTLRFileStream(path)
        return getCompilationUnit(charStream)
    }

    fun parseCode(program: String): CompilationUnit {
        val charStream = ANTLRInputStream(program)
        return getCompilationUnit(charStream)
    }

    private fun getCompilationUnit(charStream: CharStream): CompilationUnit {
        val parser = getElaginParser(charStream)
        parser.addErrorListener(ErrorListener())
        val compilationUnitVisitor = CompilationUnitVisitor()
        val compilationUnit = parser.compilationUnit()
        val errors = parser.numberOfSyntaxErrors
        if (errors !== 0) {
            throw RuntimeException("There are errors syntax errors")
        }
        return compilationUnit.accept(compilationUnitVisitor)
    }

    private fun getElaginParser(charStream: CharStream): ElaginParser {
        val lexer = ElaginLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = ElaginParser(tokenStream)
        return parser
    }
}
