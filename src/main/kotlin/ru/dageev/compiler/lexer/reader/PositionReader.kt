package ru.dageev.compiler.lexer.reader

import java.io.Reader

/**
 * Created by dageev
 *  on 03/20/2016.
 */
class PositionReader(private val reader: Reader) {
    private var line = 1
    private var character = 0

    fun getChar(): Char {
        val char = reader.read().toChar()
        if (char == '\n') {
            line++
            character = 0
        } else {
            character++
        }
        return char;
    }

    fun getPosition() = Position(line, character)
}