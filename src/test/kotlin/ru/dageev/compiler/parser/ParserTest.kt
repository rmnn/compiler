package ru.dageev.compiler.parser

import org.junit.Test

/**
 * Created by dageev
 * on 10/12/16.
 */
class ParserTest {

    @Test
    fun parse() {
        val parser = Parser()
        parser.parseCode("HelloClass { }")
    }

}