package ru.dageev.compiler

import org.junit.Test

/**
 * Created by dageev 
 * on 12/14/16.
 */
class ClassesTest extends GroovyTestCase {
    ElaginRunner elaginRunner = new ElaginRunner()

    @Test
    void testClassWithDefaultConstructor() {
        def source = """
        class A { fun printSmth() { print(0) }  }
       
        fun main() {
            var a = new A()
            a.printSmth()
        }
            """
        assert "0" == elaginRunner.run(source)
    }

    @Test
    void testClassWithComplexConstructor() {
        def source = """
        class A { 
            var a: int
            constructor(a:int) {
                this.a = a
            }
            fun printSmth() { print(a) }  
        }
       
        fun main() {
            var a = new A(777)
            a.printSmth()
        }
            """
        assert "777" == elaginRunner.run(source)
    }

    @Test
    void testSimpleClassFieldAccess() {
        def source = """
        class A { 
            var a: int
            constructor(a:int) {
                this.a = a
            }
            fun printSmth() { print(a) }  
        }
       
        fun main() {
            var a = new A(777)
            print(a.a)
        }
            """
        assert "777" == elaginRunner.run(source)
    }


    @Test
    void testClassMethodCallWithParameters() {
        def source = """
        class A { 
            var a: int
            constructor(a:int) {
                this.a = a
            }
            
            fun getA():int { return a }
            
            fun printSmth(object: A, c: int) { print(object.a + this.a  + object.getA() + a  + c) }  
        }
       
        fun main() {
            var a = new A(1)
            a.printSmth(a, 100)
        }
            """
        assert "104" == elaginRunner.run(source)
    }


    @Test
    void testClassWithPrivateFields() {
        def source = """
        class A { 
            private var a: int
            constructor(a:int) {
                this.a = a
            }
            
            fun getA():int { return a }
            
            fun setA(a: int) { this.a = a }
            
        }
       
        fun main() {
            var a = new A(1)
            var a1 = a.getA()
            a.setA(100)
            var a2 = a.getA()
            print(a1 + a2)
        }
            """
        assert "101" == elaginRunner.run(source)
    }


    @Test
    void testClassWithSeveralConstructors() {
        def source = """
        class A { 
            private var a: int
            constructor(a:int) {
                this.a = a
            }
            
            constructor() {
                this.a = 123
            }
            
            fun getA():int { return a }            
        }
       
        fun main() {
            var a1 = new A()
            var a2 = new A(100)
            print(a1.getA() + a2.getA())
            
        }
            """
        assert "223" == elaginRunner.run(source)
    }

    @Test
    void testClassInheritance() {
        def source = """
        class A { fun printSmth() { print(0) }  }
        class B : A { }
       
        fun main() {
            var a = new B()
            a.printSmth()
            
        }
            """
        assert "0" == elaginRunner.run(source)
    }


    @Test
    void testClassInheritanceWithSuperCall() {
        def source = """
        class A {
            private var a: int
            private var b: boolean
            
            public constructor(field: int, bool: boolean) {
                a = field
                this.b = bool
            }
            
            fun printA() { 
              if(b) print(a) 
            }  
        }
            
        class B : A { 
            public constructor(field: int) {
                super(2 * field, true)
            }
        }
       
        fun main() {
            var a = new B(10)
            a.printA()
            
        }
            """
        assert "20" == elaginRunner.run(source)
    }


    @Test
    void testParentClassFieldAccess() {
        def source = """
        class A {
            var a: int        
            public constructor() { a = 777 }
        }
            
        class B : A { 
            public constructor() { super() }
        }
       
        fun main() {
            var a = new B()
            print(a.a)
        }
            """
        assert "777" == elaginRunner.run(source)
    }


    @Test
    void testReturnParentClass() {
        def source = """
        class A {
            var a: int        
            public constructor() { a = 777 }
        }
            
        class B : A { 
            public constructor() { super() }
        }
       
        fun main() {
            var a = createA()
            print(a.a)
        }
        
        
        fun createA(): A {
            return new B()
        }
            """
        assert "777" == elaginRunner.run(source)
    }
}
