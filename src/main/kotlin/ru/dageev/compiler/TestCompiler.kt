package ru.dageev.compiler

/**
 * Created by dageev
 * on 12/7/16.
 */
class TestCompiler {

    tailrec fun ping(n: Int) {
        print(n)
        ping(n - 1)
    }

    tailrec fun fibHelper(x: Int, prev: Int = 0, next: Int = 1) {
        fibHelper(x - 1, next, (next + prev))
    }
}