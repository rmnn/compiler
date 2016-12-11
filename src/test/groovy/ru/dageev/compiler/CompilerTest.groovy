package ru.dageev.compiler

import org.junit.Test

/**
 * Created by dageev 
 * on 12/11/16.
 */
class CompilerTest extends GroovyTestCase {
    ElaginRunner elaginRunner = new ElaginRunner()

    @Test
    void testShouldPrint10() {
        def source = """
            fun main() { print(10) }
                    """
        assert "10" == elaginRunner.run(source).first
    }
}