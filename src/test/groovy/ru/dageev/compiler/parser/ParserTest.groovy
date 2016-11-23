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
        def source = " class First {  a: NOT_EXISTING_CLASS }"
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
            a: int
            a: int
        }
                    """
        expectException(source, "Field 'a' already exists in class 'First'")
    }

    @Test
    void testShouldFailForDuplicatedLocalVariables() {
        def source = """
        class First {
            fun function() {
               a: int = 10
               a: int = 100
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
              value = notExistingFunction(a, b)
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
        class First { private privateField: int }
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
        class First { private fun privateMethod(a:int): int { } }
        class Second: First {
            fun function() {
               a: int = privateMethod(100)
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
               a: int = notExistingMethod()
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
               a: int = 10
               b : int = a.primitive
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
               a: int = 10
               b : int = a.primitiveMethod()
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
               a: int = notExistingLocalVariable
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
                a:void = 100
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
            fun function2() {  b: int = function(true) }    
        }
                    """
        expectException(source, "Method 'function[boolean]' not found for class 'First'")
    }

    @Test
    void testShouldFailIncorrectConstructorCall() {
        def source = """
        class First {
            fun function() { a : First = new First(10) }  
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
            fun function() { a: First = new First() }
        }
                    """
        expectException(source, "Could not find matching constructor with arguments [] for First")
    }


    private void expectException(String source, String expectedMessage) {
        def message = shouldFail(CompilationException) {
            parser.parseCode(source)
        }
        assert expectedMessage == message

    }
}
