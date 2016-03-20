package ru.dageev.compiler.lexer.token

/**
 * Created by dageev
 *  on 03/20/2016.
 */
enum class TokenType(val string: String, val operationType: OperationType = OperationType.UNDEFINED) {

    UNDEFINED("UNDEFINED"),

    // SPECIAL
    ERROR("ERROR"),
    EOF("EOF"),
    IDENTIFIER("identifier"),
    INTEGER("integer"),

    // types
    BOOLEAN("boolean", OperationType.KEYWORD),
    INT("int", OperationType.KEYWORD),

    TRUE("true", OperationType.KEYWORD),
    FALSE("false", OperationType.KEYWORD),


    // arithmetic expressions
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    MODULO("%"),
    LESS("<", OperationType.BINARY),
    GREATER(">", OperationType.BINARY),
    EQUAL("==", OperationType.BINARY),
    LESS_EQUAL("<=", OperationType.BINARY),
    GREATER_EQUAL(">=", OperationType.BINARY),
    NOT_EQUAL("!=", OperationType.BINARY),
    LOGICAL_AND("&&", OperationType.BINARY),
    LOGICAL_OR("||", OperationType.BINARY),
    COMMA(","),


    // operators
    EMPTY_OP(";"),
    ASSIGN("="),
    IF("if", OperationType.KEYWORD),
    ELSE("else", OperationType.KEYWORD),
    WHILE("while", OperationType.KEYWORD),
    FOR("for", OperationType.KEYWORD),
    READ("read", OperationType.KEYWORD),
    WRITE("write", OperationType.KEYWORD),
    SKIP("skip", OperationType.KEYWORD),


    // blocks
    LEFT_BRACKET("{"),
    RIGHT_BRACKET("}"),
    LP("("),
    RP(")")


}