grammar Elagin;

@header {
   package ru.dageev.compiler.grammar;
}

compilationUnit : (classDeclaration)*
    ;

accessModifier
    :   'public'
    |   'private'
    ;

classDeclaration
    :   'class' Identifier parentClassDeclaration? classBody
    ;

parentClassDeclaration
    : ':' Identifier
    ;

classBody
    :   '{' fieldDeclaration* methodDeclaration* constructorDeclaration*   '}'
    ;

methodDeclaration
    :   (accessModifier)? Identifier formalParameters (':' type)?
        (   methodBody
        |   ';'
        )
    ;

constructorDeclaration
    :   (accessModifier)? 'constructor' formalParameters constructorBody
    ;

fieldDeclaration
    :   (accessModifier)? Identifier ':' type';'
    ;

type
    :   classType
    |   primitiveType
    ;

classType
    :   Identifier
    ;

primitiveType
    :   'boolean'
    |   'int'
    |   'void'
    ;

formalParameters
    :   '(' formalParameterList? ')'
    ;

formalParameterList
    :   formalParameter (',' formalParameter)*
    ;

formalParameter
    :  Identifier ':' type
    ;

methodBody
    :   block
    ;

constructorBody
    :   block
    ;

block : '{' statement* '}' ;

statement : block
           | localVariableDeclarationStatement
           | assignment
           | print
           | read
           | returnStatement
           | ifStatement
           | whileStatement
           | expression ;

localVariableDeclarationStatement
    :  Identifier ':' type '=' expression ';'
    ;



assignment
    :   Identifier '=' expression ';'
    ;

ifStatement
    :   'if' parExpression statement ('else' statement)?
    ;

whileStatement
    :   'while' parExpression statement
    ;

returnStatement
    :   'return' expression? ';'
    ;

parExpression
    :   '(' expression ')'
    ;

print
    : 'print' '(' expression ')' ';'
    ;

read
    :   'read' '(' Identifier ')' ';'
    ;

expression
    :   Identifier #variableReference
    |   value #valueExpr
    |   classRef=expression '.' Identifier '(' expressionList? ')' #methodCall
    |   Identifier '(' expressionList? ')' #methodCall
    |   'new' Identifier '(' expressionList? ')' #constructorCall
    |   expression operation=('*'|'/'| '%') expression #multDivExpression
    |   expression operation=('+'|'-') expression #sumExpression
    |   expression operation=('<'|'>'|'>='|'<='|'!='|'==') expression #compareExpression
    |   expression operation=('&&'|'||') expression #logicalExpression
    ;

expressionList
    :   expression (',' expression)*
    ;

value
    :   IntegerLiteral
    |   BooleanLiteral
    ;

IntegerLiteral
    :   '0'
    |   NonZeroDigit (Digits?)
    ;

Identifier
    :   Letter (Letter | Digit)*
    ;

fragment
Letter
    :   [a-zA-Z_]
    ;

fragment
Digits
    :   Digit (Digit)?
    ;

fragment
Digit
    :   '0'
    |   NonZeroDigit
    ;

fragment
NonZeroDigit
    :   [1-9]
    ;


BooleanLiteral
    :   'true'
    |   'false'
    ;


WS  :  [ \t\r\n\u000C]+ -> skip
    ;

COMMENT
    :   '/*' .*? '*/' -> skip
    ;

LINE_COMMENT
    :   '//' ~[\r\n]* -> skip
    ;