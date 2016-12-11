package ru.dageev.compiler.parser

import org.junit.Test

/**
 * Created by dageev 
 * on 11/23/16.
 */
class ParserTest extends GroovyTestCase {

    def parser = new Parser()

    @Test
    void testShouldBeSuccessForAllLanguageIdioms() {
        def source = this.getClass().getResource('/correctCode.elg').text
        parser.parseCode(source)
    }

    @Test
    void testShouldFailForNotExistingClass() {
        def source = " class First {  var a: NOT_EXISTING_CLASS }"
        expectException(source, "Class NOT_EXISTING_CLASS not exists")
    }

    @Test
    void testShouldFailForNotExistingClassInClassInInheritance() {
        def source = "class First: NOT_EXISTING_CLASS {  }"
        expectException(source, "Parent class NOT_EXISTING_CLASS not exists for First")
    }

    @Test
    void testShouldFailForDuplicatedFields() {
        def source = """
        class First {
            var a: int
            var a: int
        }
                    """
        expectException(source, "Field 'a' already exists in class 'First'")
    }

    @Test
    void testShouldFailForDuplicatedLocalVariables() {
        def source = """
        class First {
            fun function() {
               var a: int = 10
               var a: int = 100
            }
        }
                    """
        expectException(source, "Local variable 'a' already exists in class 'First'")
    }

    @Test
    void testShouldFailForDuplicatedMethodSignatures() {
        def source = """
        class First {
            fun function(a: int, b: int, c:boolean) {}
           
            fun function(newA: int, newB: int, newC:boolean) {}
        }
                    """
        expectException(source, "Function signature 'public function(int, int, boolean): void' already exists for First")
    }

    @Test
    void testShouldFailForNotExistingMethodSignatureCall() {
        def source = """
        class First {
            fun function(a: int, b: int, c:boolean) {
              var value : int = notExistingFunction(a, b)
            }          
        }
                    """
        expectException(source, "Method 'notExistingFunction[int, int]' not found for class 'First'")
    }

    @Test
    void testShouldFailForNotExistingFieldReference() {
        def source = """
        class First {
            fun function() {
               this.notExistingField = 100
            }          
        }
                    """
        expectException(source, "Field 'notExistingField' for 'First' not exists")
    }

    @Test
    void testShouldFailForAccessParentPrivateField() {
        def source = """
        class First { private var privateField: int }
        class Second: First {
            fun function() {
               this.privateField = 100
            }          
        }
                    """
        expectException(source, "Unable to get access private field 'privateField' of class 'First'")
    }


    @Test
    void testShouldFailForCallPrivateParentMethods() {
        def source = """
        class First { private fun privateMethod(a:int): int { return a } }
        class Second: First {
            fun function() {
               var a: int = privateMethod(100)
            }          
        }
                    """
        expectException(source, "Unable to call parent 'First' class private method 'privateMethod'")
    }

    @Test
    void testShouldFailForCallNotExistingMethod() {
        def source = """
        class Second {
            fun function() {
               var a: int = notExistingMethod()
            }          
        }
                    """
        expectException(source, "Method 'notExistingMethod[]' not found for class 'Second'")
    }


    @Test
    void testShouldFailForAccessFieldsOfPrimitiveTypes() {
        def source = """
        class First {
            fun function() {
               var a: int = 10
               var b : int = a.primitive
            }          
        }
                    """
        expectException(source, "Unable to access field of primitive types")
    }


    @Test
    void testShouldFailForCallMethodsOfPrimitiveTypes() {
        def source = """
        class First {
            fun function() {
               var a: int = 10
               var b : int = a.primitiveMethod()
            }          
        }
                    """
        expectException(source, "Unable to call methods for primitive types for")
    }

    @Test
    void testShouldFailForNotExistingLocalVariable() {
        def source = """
        class First {
            fun function() {
               var a: int = notExistingLocalVariable
            }          
        }
                    """
        expectException(source, "Local variable 'notExistingLocalVariable' not found for class 'First'")
    }

    @Test
    void testShouldFailForNotBooleanConditionInIfExpression() {
        def source = """
        class First {
            fun function() {
               if (10) { } else { }
            }          
        }
                    """
        expectException(source, "Type INT could not be used for condition")
    }

    @Test
    void testShouldFailForNotBooleanConditionInWhileExpression() {
        def source = """
        class First {
            fun function() {
               while (10) { } 
            }          
        }
                    """
        expectException(source, "Type INT could not be used for condition")
    }


    @Test
    void testShouldFailForDeclarationVariableWithVoidType() {
        def source = """
        class First {
            fun function() {
                var a:void = 100
            }         
        }
                    """
        expectException(source, "Variable 'a' could not have VOID type")
    }

    @Test
    void testShouldFailForIncorrectParametersTypeCall() {
        def source = """
        class First {
            fun function(a: int):int { return a }  
            fun function2() { var b: int = function(true) }    
        }
                    """
        expectException(source, "Method 'function[boolean]' not found for class 'First'")
    }

    @Test
    void testShouldFailIncorrectConstructorCall() {
        def source = """
        class First {
            fun function() {var a : First = new First(10) }  
        }
                    """
        expectException(source, "Could not find matching constructor with arguments [int] for First")
    }

    @Test
    void testShouldFailForCallParentPrivateConstructor() {
        def source = """
        class First {
           private constructor() {}
        }
        
        class Second { 
            fun function() {var a: First = new First() }
        }
                    """
        expectException(source, "Could not find matching constructor with arguments [] for First")
    }

    @Test
    void testShouldFailForDuplicatedClassDeclaration() {
        def source = """
        class First {
           fun main() { }
        }
        
        class First { 
        }
                    """
        expectException(source, "Found duplicated class declaration for class 'First'")
    }

    @Test
    void testShouldFailForDuplicatedClassConstructorDeclaration() {
        def source = """
        class First {
           constructor(a:int) { }
           constructor(b:int) { }
        }
                    """
        expectException(source, "Constructor signature 'public First(int): void' already exists for First")
    }

    @Test
    void testShouldFailForNonIntArgumentInArithmeticOperation() {
        def source = """
        class First {
           fun main() {var a: int = 1 + true }
        }
                    """
        expectException(source, "Incorrect right expression type for operation '+'. Expected 'int', found 'boolean'")
    }

    @Test
    void testShouldFailForNonBooleanArgumentInLogicalOperation() {
        def source = """
        class First {
           fun main() {var a: boolean = new First() && true }
        }
                    """
        expectException(source, "Incorrect left expression type for operation '&&'. Expected 'boolean', found 'First'")
    }


    @Test
    void testShouldFailForNonVoidMethodWithoutReturn() {
        def source = """
        class First {
           fun function():int { }
        }
                    """
        expectException(source, "Method public function(): int should have return statement at the end")
    }


    @Test
    void testShouldFailForNonVoidMethodWithoutReturnInElseSection() {
        def source = """
        class First {
           fun function():int { 
              if (2 > 3) {
                return 10
              } else { 
              }
           }
        }
                    """
        expectException(source, "Method public function(): int should have return statement at the end")
    }

    @Test
    void testShouldFailForNonVoidMethodWithoutReturnInWhileSection() {
        def source = """
        class First {
           fun function():int { 
              while(2 > 3) {              
              }
           }
        }
                    """
        expectException(source, "Method public function(): int should have return statement at the end")
    }

    @Test
    void testShouldBeOkForReturnInIfStatement() {
        def source = """
        class First {
           fun function():int { 
              if (2 > 3) {
                return 10
              } else { 
                return 30
              }
           }
        }
                    """
        parser.parseCode(source)

    }

    @Test
    void testShouldBeOkForReturnInWhileStatement() {
        def source = """
        class First {
           fun function():int { 
              while(2 > 3) {  
                return 10        
              }
           }
        }
                    """
        parser.parseCode(source)

    }

    @Test
    void testShouldFailForCallSuperMethodWithoutParentClass() {
        def source = """
        class First {
            constructor() {
                super()
            }
        }
                    """
        expectException(source, "Could not make super() call for class without parent in class First")
    }

    @Test
    void testShouldFailForCallSuperMethodForParentPrivateConstructor() {
        def source = """
        class First {
            private constructor(a: int) {
                
            }
        }
        
        class Second: First{
            constructor() {
                super(10)
            }
        }
                    """
        expectException(source, "Unable to call parent 'First' class private constructor")
    }


    @Test
    void testShouldFailForCallSuperMethodForNotExistingParentConstructor() {
        def source = """
        class First {
            constructor(a: int) {
                
            }
        }
        
        class Second: First{
            constructor() {
                super(true)
            }
        }
                    """
        expectException(source, "Constructor 'First[boolean]' not found for class 'Second'")
    }


    @Test
    void testShouldFailForTailRecOptimizationWithoutRecCalls() {
        def source = """
        tailrec fun hello(): int { return 100 } 
                    """
        expectException(source, "Unable to do tailrec optimization for non recursive function 'public hello(): int'")
    }

    @Test
    void testShouldFailForTailrecOptimizationWithoutTailRecCalls() {
        def source = """
        tailrec fun hello(a : int): int { if (a > 10) return a else return 2 + hello(a + 10) } 
                    """
        expectException(source, "Function marked as tailrec but no tailrec call found for 'public hello(int): int'")
    }

    private void expectException(String source, String expectedMessage) {
        def message = shouldFail(CompilationException) {
            parser.parseCode(source)
        }
        assert expectedMessage == message

    }
}
