grammar Elagin;

compilationUnit : classDeclaration EOF;

accessModifier
    :   'public'
    |   'private'
    |   'abstract'
    ;

classDeclaration
    :   Identifier classBody
    ;

classBody
    :   '{' classBodyDeclaration* '}'
    ;

classBodyDeclaration
    :   accessModifier* memberDeclaration
    |   ';'
    ;

memberDeclaration
    :   methodDeclaration
    |   fieldDeclaration
    |   constructorDeclaration
    ;

methodDeclaration
    :   (type)? Identifier formalParameters
        (   methodBody
        |   ';'
        )
    ;

constructorDeclaration
    :   Identifier formalParameters constructorBody
    ;

fieldDeclaration
    :   type variableDeclarators ';'
    ;

variableDeclarators
    :   variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    :   Identifier ('=' expression)?
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
    :   type Identifier
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
    :    localVariableDeclaration ';'
    ;

localVariableDeclaration
    :    type variableDeclarators
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
    :   primary
    |   expression '.' Identifier
    |   expression '(' expressionList? ')'
    |   'new' creator
    |   expression ('*'|'/'| '%') expression
    |   expression ('+'|'-') expression
    |   expression ('<'|'>'|'>='|'<=') expression
    |   expression ('!='|'==') expression
    |   expression ('&&'|'||') expression
    ;

primary
    :   '(' expression ')'
    |   literal
    |   Identifier
    ;

creator
    : Identifier arguments
    ;

arguments
    :   '(' expressionList? ')'
    ;

expressionList
    :   expression (',' expression)*
    ;

literal
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