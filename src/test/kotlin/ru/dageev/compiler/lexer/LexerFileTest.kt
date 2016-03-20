package ru.dageev.compiler.lexer

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.dageev.compiler.lexer.token.TokenType
import ru.dageev.compiler.lexer.util.getLexer

/**
 * Created by dageev
 *  on 03/20/2016.
 */
class LexerFileTest {


    @Test
    fun testCorrectProgramFromFile() {
        val programText = getProgram("/correct-program.preview")
        assertTrue(programHasNoLexErrors(programText))
    }

    @Test
    fun testIncorrectProgramFromFile() {
        val programText = getProgram("/incorrect-program.preview")
        assertFalse(programHasNoLexErrors(programText))
    }

    private fun getProgram(name: String) = this.javaClass.getResource(name).readText()

    fun programHasNoLexErrors(program: String): Boolean {
        val lexer = getLexer(program)
        var token = lexer.getNextToken()
        while (token.type != TokenType.EOF) {
            if (token.type == TokenType.ERROR) {
                return false
            }
            token = lexer.getNextToken()
        }
        return true
    }
}