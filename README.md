# Welcome to the Elagin Compiler Project

This project is developed during a practical course about compiler development at ITMO University.
[![Build Status](https://travis-ci.org/rmnn/compiler.svg?branch=master)](https://travis-ci.org/rmnn/compiler)

### Build 

```bash
./gradlew build
```


### Compile .elg program

```bash
java -jar build/libs/compiler-all-1.0.jar Example.elg
```

### Run

```bash
java -cp target ElaginProgram 
```

## Language Definition

This is simple jvm-based language with classes support

#### Types:
* int
* boolean
* void
* classType


#### Operations:
* arithmetic operations (+,-, /, *, %)
* logical Operations (&&, ||, ==, !=, >, <, >=, <=)


#### Statements
* block
* conditional (if-else)
* while 
* read\print
* variable/Method declaration
* assignments
* return


#### Classes

* multiple constructors
* private\public fields
* private\public methods
* class inheritance
* super call


#### Tailrec optimization

* simple tailrec optimization
* accumulator detection



## Examples



#### Functions
```kotlin
/* 
 * simple factorial program
 * with read and some value checks
*/
fun main() {
    var n: int = 1000
    read(n)
    var factorialOfN = fact(n)

    if (n < 7) {
        while(factorialOfN < 1000) {
            n = n + 1
            factorialOfN = fact(n)
        }
    }
    print(n)
}


fun fact(n: int) : int {
    if (n == 1) return 1
    else return n * fact(n - 1)
}
```          



#### Classes
```kotlin
class Value { 
  private var value: int
  
  constructor(value: int) { 
    this.value = value 
  }
  
  fun getValue() : int { return value }
}


class ValueWithSqr : Value {
    constructor(value: int) { super(value) }
    
    fun getSqr() : int { 
        return getValue() * getValue()
    }
}   
 
class Printer {
    fun printValue(value: ValueWithSqr) {  print(value.getSqr()) }
}

fun main() {
    var value = new ValueWithSqr(10)
    var sqrPrinter = new Printer()
    sqrPrinter.printValue(value)
}
``` 

### tailrec functions
```kotlin
tailrec fun fact(n: int) : int {
    if (n == 1) return 1
    else return n * fact(n - 1)
}

fun main() {
    print(fact(5))
}
```          
