# Welcome to the Elagin Compiler Project

This project is developed during a practical course about compiler development at ITMO University.


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

```kotlin
class Value {
    private var value: int
    constructor(value: int) { this.value = value }
        fun getValue() : int { return value }
    }

    class SqrPrinter {
        fun sqr(value: Value) { print(value.getVal() * value.getVal()) }
    }

    fun main() {
        var value = new Value(10)
        var sqrPrinter = new SqrPrinter()
        sqrPrinter.sqr(value)
    }
```          

```kotlin
tailrec fun fact(n: int) : int {
    if (n == 1) return 1
    else return n * fact(n - 1)
}

fun main() {
    print(fact(5))
}
```          
