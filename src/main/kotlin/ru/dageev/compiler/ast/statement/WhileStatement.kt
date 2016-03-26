package ru.dageev.compiler.ast.statement

import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
class WhileStatement(val condition: Expression, val body: Statement, position: Position) : Statement(position)