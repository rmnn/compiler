package ru.dageev.compiler.lexer

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.dageev.compiler.lexer.reader.PositionReader
import java.io.StringReader

/**
 * Created by dageev
 *  on 03/22/2016.
 */

class PositionReaderTest {

    @Test
    fun testReader() {
        val string = "abcdefg"
        val positionReader = getPositionReader(string)
        for (i in 1..7) {
            assertEquals(string[i - 1], positionReader.getChar())
            assertEquals(i, positionReader.getPosition().character)
        }
    }

    private fun getPositionReader(string: String): PositionReader {
        val reader = StringReader(string)
        val positionReader = PositionReader(reader)
        return positionReader
    }

    @Test
    fun testFewLines() {
        val string = "a\nb\nc"
        val positionReader = getPositionReader(string)
        for (i in 1..1) {
            assertEquals(string[i - 1], positionReader.getChar())
            assertEquals(i, positionReader.getPosition().line)
            assertEquals(i, positionReader.getPosition().character)

        }
    }

}