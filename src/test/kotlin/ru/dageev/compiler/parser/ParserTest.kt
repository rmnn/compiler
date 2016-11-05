package ru.dageev.compiler.parser

import org.junit.Test

/**
 * Created by dageev
 * on 10/12/16.
 */
class ParserTest {

    @Test
    fun classesShouldParsedCorrectly() {
        val parser = Parser()

        val program = parser.parse("/home/dageev/workspace/compiler/src/test/resourcces/text.elg")
        println(program)
    }

}