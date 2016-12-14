package ru.dageev.compiler

import org.junit.Test

/**
 * Created by dageev 
 * on 12/14/16.
 */
class TailRecOptimizationTest {
    ElaginRunner elaginRunner = new ElaginRunner()

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
        assert "120" == elaginRunner.run(source)
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
        assert "120" == elaginRunner.run(source)
    }


    @Test
    void testTailrecFactorialWithAccumulatorDetection() {
        def source = """
        tailrec fun fact(n: int) : int {
            if (n == 1) return 1
            else return n * fact(n - 1)
        }

        fun main() {
            print(fact(5))
        }
            """
        assert "120" == elaginRunner.run(source)
    }


    @Test
    void testShouldFailWithStackOverflowException() {
        def source = """
        fun fact(n: int) : int {
            if (n == 100) return 1
            else return fact(n)
        }

        fun main() {
            print(fact(1))
        }
            """
        def exception = elaginRunner.run(source)
        assert exception.contains("java.lang.StackOverflowError")
    }

    @Test
    void testShouldBeInInfinityLoopWithTailRecOptimization() {
        def source = """
        tailrec fun fact(n: int) : int {
            if (n == 100) return 1
            else return fact(n)
        }

        fun main() {
            print(fact(1))
        }
            """
        def exception = elaginRunner.run(source)
        assert exception.isEmpty()
    }

    @Test
    void testShouldFailWithStackOverflowExceptionWithoutTailRecOptimization() {
        def source = """
        fun fact(n: int) : int {
            if (n == 100) return 1
            else return n * fact(n)
        }

        fun main() {
            print(fact(1))
        }
            """
        def exception = elaginRunner.run(source)
        assert exception.contains("java.lang.StackOverflowError")
    }


    @Test
    void testShouldBeInInfinityLoopWithTailRecWitAccumulatorDetectionOptimization() {
        def source = """
        tailrec fun fact(n: int) : int {
            if (n == 100) return 1
            else return n * fact(n)
        }

        fun main() {
            print(fact(1))
        }
            """
        def exception = elaginRunner.run(source)
        assert exception.isEmpty()
    }
}
