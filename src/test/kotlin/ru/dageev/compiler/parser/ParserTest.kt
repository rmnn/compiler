package ru.dageev.compiler.parser

import org.junit.Assert.assertFalse
import org.junit.Test

/**
 * Created by dageev
 * on 10/12/16.
 */
class ParserTest {

    @Test
    fun classesShouldParsedCorrectly() {
        val parser = Parser()

        val code = """
            class First {}
            class Second : First {} """

        val program = parser.parseCode(code)

        assert(program.classDeclarations.size == 2)
        assert(program.classDeclarations[0].name == "First")
        assertFalse(program.classDeclarations[0].parentClassDeclaration.isPresent)
        assert(program.classDeclarations[1].name == "Second")
        assert(program.classDeclarations[1].parentClassDeclaration.get().name == "First")
    }

}