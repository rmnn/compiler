grammar Elagin;

@header {
   package ru.dageev.compiler.grammar;
}

compilationUnit : (classDeclaration)* (methodDeclaration)*
    ;

accessModifier
    :   'public'
    |   'private'
    ;

classDeclaration
    :   'class' identifier parentClassDeclaration? classBody
    ;

parentClassDeclaration
    : ':' identifier
    ;

classBody
    :   '{' (fieldDeclaration | methodDeclaration | constructorDeclaration)*  '}'
    ;

methodDeclaration
    :   (accessModifier)? (tailrecModifier)? 'fun' identifier formalParameters (':' type)? methodBody
    ;

tailrecModifier
    : 'tailrec'
    ;

constructorDeclaration
    :   (accessModifier)? 'constructor' formalParameters constructorBody
    ;

fieldDeclaration
    :   (accessModifier)? 'var' identifier ':' type
    ;

type
    :   classType
    |   primitiveType
    ;

classType
    :   identifier
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
    :   identifier ':' type
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
    :  'var' identifier (':' type)? '=' expression
    ;



assignment
    :   identifier '=' assignmentExpr= expression
    |   classExpr=expression '.' identifier '=' assignmentExpr=expression
    ;

ifStatement
    :   'if' parExpression statement ('else' statement)?
    ;

whileStatement
    :   'while' parExpression statement
    ;

returnStatement
    :   'return' expression?
    ;

parExpression
    :   '(' expression ')'
    ;

print
    : 'print' '(' expression ')'
    ;

read
    :   'read' '(' identifier ')'
    ;

expression
    :   booleanLiteral #booleanValue
    |   IntegerLiteral #integerValue
    |   '(' expression ')' #parenthesis
    |   classRef=expression '.' identifier '(' expressionList? ')' #methodCall
    |   expression '.' identifier #fieldAccessor
    |   identifier '(' expressionList? ')' #methodCall
    |   'new' identifier '(' expressionList? ')' #constructorCall
    |   expression operation=('*'|'/'| '%') expression #multDivExpression
    |   expression operation=('+'|'-') expression #sumExpression
    |   expression operation=('<'|'>'|'>='|'<='|'!='|'==') expression #compareExpression
    |   expression operation=('&&'|'||') expression #logicalExpression
    |   'super' '('expressionList? ')' #superCall
    |   identifier #variableReference

    ;


expressionList
    :   expression (',' expression)*
    ;

IntegerLiteral       : [+-]?('0'|[1-9][0-9]*) ;

booleanLiteral : ('true' | 'false')   ;

identifier
    : ID
    ;

ID : [_a-zA-Z][-_a-zA-Z0-9]* ;

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


WS  :  [ \t\r\n\u000C]+ -> skip ;
COMMENT :   '/*' .*? '*/' -> skip  ;
LINE_COMMENT :   '//' ~[\r\n]* -> skip ;