package ru.dageev.compiler.lexer

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.dageev.compiler.lexer.token.OperationType
import ru.dageev.compiler.lexer.token.TokenType
import ru.dageev.compiler.lexer.util.getLexer

/**
 * Created by dageev
 *  on 03/20/2016.
 */

class LexerTest {

    @Test
    fun testEmpty() {
        val lexer = getLexer("")
        assertEquals(TokenType.EOF, lexer.getNextToken().type)
    }

    @Test
    fun testComment() {
        val lexer = getLexer("#helllo \r\n #helllo \r\n  int a")
        assertEquals(TokenType.INT, lexer.getNextToken().type)
        assertEquals(TokenType.IDENTIFIER, lexer.getNextToken().type)
    }

    @Test
    fun testKeywordTokens() {
        val keywordTokens = TokenType.values().filter { it.operationType == OperationType.KEYWORD }
        val keywordString = buildString {
            keywordTokens.forEach {
                append("${it.string} ")
            }
        }
        val lexer = getLexer(keywordString)

        var token = lexer.getNextToken()
        var i = 0;
        while (token.type != TokenType.EOF) {
            assertEquals(keywordTokens[i], token.type)
            i++
            token = lexer.getNextToken()
        }
    }

    @Test
    fun testBinaryOperationTokens() {
        val operationTokens = TokenType.values().filter { it.operationType == OperationType.BINARY }
        val binaryOperations = buildString {
            operationTokens.forEach {
                append("${it.string} ")
            }
        }
        val lexer = getLexer(binaryOperations)
        operationTokens.forEach {
            val token = lexer.getNextToken()
            assertEquals(it, token.type)
        }
    }

    @Test
    fun testArithmeticOperationTokens() {
        val arithmeticOperations = "+ - / * , % "
        val lexer = getLexer(arithmeticOperations)
        assertEquals(TokenType.ADD, lexer.getNextToken().type)
        assertEquals(TokenType.SUBTRACT, lexer.getNextToken().type)
        assertEquals(TokenType.DIVIDE, lexer.getNextToken().type)
        assertEquals(TokenType.MULTIPLY, lexer.getNextToken().type)
        assertEquals(TokenType.COMMA, lexer.getNextToken().type)
        assertEquals(TokenType.MODULO, lexer.getNextToken().type)
        assertEquals(TokenType.EOF, lexer.getNextToken().type)
    }

    @Test
    fun testIntegerTokens() {
        val integers = buildString {
            for (i in 1..1000) {
                append(i.toString() + " ")
            }
        }
        val lexer = getLexer(integers)
        for (i in 1..1000) {
            val token = lexer.getNextToken()
            assertEquals(TokenType.INTEGER, token.type)
            assertEquals(i.toString(), token.value)
        }
        assertEquals(TokenType.EOF, lexer.getNextToken().type)
    }

    @Test
    fun testIdentifier() {
        val lexer = getLexer("ident secondIndent")
        val token1 = lexer.getNextToken()
        assertEquals(TokenType.IDENTIFIER, token1.type)
        assertEquals("ident", token1.value)

        val token2 = lexer.getNextToken()
        assertEquals(TokenType.IDENTIFIER, token2.type)
        assertEquals("secondIndent", token2.value)

    }


}