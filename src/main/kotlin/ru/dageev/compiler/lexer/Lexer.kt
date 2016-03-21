package ru.dageev.compiler.lexer

import ru.dageev.compiler.lexer.reader.PositionReader
import ru.dageev.compiler.lexer.token.OperationType
import ru.dageev.compiler.lexer.token.Token
import ru.dageev.compiler.lexer.token.TokenType
import java.io.Reader

/**
 * Created by dageev
 *  on 03/20/2016.
 */
class Lexer(val reader: Reader) {
    var currChar: Char
    val positionReader: PositionReader

    init {
        positionReader = PositionReader(reader)
        currChar = positionReader.getChar()
    }


    fun getNextToken(): Token {
        while (whitespace()) {
            nextChar()
        }

        if (isLetter()) {
            return readToken(TokenType.IDENTIFIER, isLetterFunction())
        } else if (isDigit()) {
            return readToken(TokenType.INTEGER, isDigitFunction())
        } else if (endOfFile()) {
            reader.close()
            return token(tokenType = TokenType.EOF)
        } else if (isComment()) {
            skipComment()
            return getNextToken()
        } else {
            return readOperatorToken()
        }
    }

    private fun readOperatorToken(): Token {
        return when (currChar) {
            '+' -> {
                nextChar()
                token(tokenType = TokenType.ADD)
            }
            '-' -> {
                nextChar()
                token(tokenType = TokenType.SUBTRACT)
            }
            '*' -> {
                nextChar()
                token(tokenType = TokenType.MULTIPLY)
            }
            '/' -> {
                nextChar()
                token(tokenType = TokenType.DIVIDE)
            }
            '!' -> {
                nextChar()
                if (currChar == '=') {
                    nextChar()
                    token(tokenType = TokenType.NOT_EQUAL)
                } else {
                    token(TokenType.ERROR, "Unexpected char '!', May be you mean '!='?")
                }
            }
            '%' -> {
                nextChar()
                token(tokenType = TokenType.MODULO)
            }
            '&' -> {
                nextChar()
                if (currChar == '&') {
                    nextChar()
                    token(tokenType = TokenType.LOGICAL_AND)
                } else {
                    token(TokenType.ERROR, "Unexpected char '&', May be you mean '&&'?")
                }
            }
            '|' -> {
                nextChar()
                if (currChar == '|') {
                    nextChar()
                    token(tokenType = TokenType.LOGICAL_OR)
                } else {
                    token(TokenType.ERROR, "Unexpected char '|', May be you mean '||'?")
                }
            }
            '(' -> {
                nextChar()
                token(tokenType = TokenType.LP)
            }
            ')' -> {
                nextChar()
                token(tokenType = TokenType.RP)
            }
            ',' -> {
                nextChar()
                token(tokenType = TokenType.COMMA)
            }
            ';' -> {
                nextChar()
                token(tokenType = TokenType.EMPTY_OP)
            }
            '=' -> {
                nextChar()
                if (currChar == '=') {
                    nextChar()
                    token(tokenType = TokenType.EQUAL)
                } else {
                    token(tokenType = TokenType.ASSIGN)
                }
            }
            '<' -> {
                nextChar()
                if (currChar == '=') {
                    nextChar()
                    token(tokenType = TokenType.LESS_EQUAL)
                } else {
                    token(tokenType = TokenType.LESS)
                }
            }
            '>' -> {
                nextChar()
                if (currChar == '=') {
                    nextChar()
                    token(tokenType = TokenType.GREATER_EQUAL)
                } else {
                    token(tokenType = TokenType.GREATER)
                }
            }
            '{' -> {
                nextChar()
                token(tokenType = TokenType.LEFT_BRACKET)
            }
            '}' -> {
                nextChar()
                token(tokenType = TokenType.RIGHT_BRACKET)
            }
            else -> {
                val errorMessage = "Unexpected char '$currChar', May be you mean '&&'?"
                nextChar()
                token(TokenType.ERROR, errorMessage)
            }
        }
    }

    private fun isComment() = currChar == '#'

    private fun isDigit() = isDigitFunction().invoke()

    private fun isLetter() = isLetterFunction().invoke()

    private fun readToken(tokenType: TokenType, expr: () -> Boolean): Token {
        val value = buildString {
            while (expr.invoke()) {
                append(currChar)
                nextChar()
            }
        }
        val foundKeyWork = TokenType.values().filter { it.operationType == OperationType.KEYWORD }.find { it.string == value }
        return if (foundKeyWork != null) {
            return token(tokenType = foundKeyWork, value = value)
        } else {
            token(tokenType = tokenType, value = value)
        }
    }

    private fun skipComment() {
        while (currChar != '\n' && currChar.toInt() != -1) {
            nextChar()
        }
        if (isComment()) {
            skipComment()
        }
    }

    private fun token(tokenType: TokenType, value: String = tokenType.string): Token = Token(type = tokenType, value = value, position = positionReader.getPosition())


    private fun endOfFile() = currChar.toInt() == 65535

    private fun isDigitFunction(): () -> Boolean = { currChar in '0'..'9' }

    private fun isLetterFunction(): () -> Boolean = { currChar in 'a'..'z' || currChar in 'A'..'Z' || currChar == '_' }


    private fun nextChar() {
        currChar = positionReader.getChar()
    }

    private fun whitespace(): Boolean = currChar == ' ' || currChar == '\t' || currChar == '\n' || currChar == '\r'


}

