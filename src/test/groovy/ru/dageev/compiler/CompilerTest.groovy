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

    @Test
    void testFactorial() {
        def source = """
        fun fact(n: int) : int {
            if (n == 1) return 1
            else return n * fact(n - 1)
        }

        fun main() {
            print(fact(5))
        }
            """
        assert "120" == elaginRunner.run(source).first
    }

    @Test
    void testTailrecFactorial() {
        def source = """
        tailrec fun fact(accum: int, n: int) : int {
            if (n == 1) return accum
            else return fact(n * accum, n - 1)
        }

        fun main() {
            print(fact(1, 5))
        }
            """
        assert "120" == elaginRunner.run(source).first
    }


}