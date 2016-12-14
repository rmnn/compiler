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
        assert "10" == elaginRunner.run(source)
    }


    @Test
    void testArithmeticOperations() {
        int a = 2
        int b = 10
        int c = 1 + 2 * 2 + b % a - 1 + 50 / 5 - (a + b) * 2

        def source = """
        fun main() {
            var a = 2
            var b = 10
            var c = 1 + 2 * 2 + b % a - 1 + 50 / 5 - (a + b) * 2
            print(c) 
        }
                    """
        assert c == elaginRunner.run(source).toInteger()
    }

    @Test
    void testLogicalOperations() {
        boolean b = false
        boolean c = 2 < 10 || b || (10 >= 1) && (1 + 1 == 2)
        def source = """
        fun main() {
            var b = false
            var c = 2 < 10 || b || (10 >= 1) && (1 + 1 == 2)
            print(c) 
        }
                    """
        assert c == elaginRunner.run(source).toBoolean()
    }

    @Test
    void testIfCondition() {
        def source = """
        fun main() {
            var b = 777
            if (b - 777 == 0) {
                print(b) 
            } else {
                print(0)
            }
        }
                    """
        assert "777" == elaginRunner.run(source)
    }

    @Test
    void testWhileMethod() {
        def source = """
        fun main() {
            var b = 777
            var a = -1
            while (b != 0) {
                b = b + a
            }
            print(b)
        }
             """
        assert "0" == elaginRunner.run(source)
    }


    @Test
    void testMethodCall() {
        def source = """
        fun sqr(a: int) : int { return a * a }

        fun main() {
            var result = sqr(2)
            print(result)
        }
             """
        assert "4" == elaginRunner.run(source)
    }

}