package ru.dageev.compiler.ast.statement

import ru.dageev.compiler.lexer.reader.Position

/**
 * Created by dageev
 *  on 03/26/2016.
 */
class IfStatement(val condition: Expression, val ifBranch: Statement, val elseBranch: Statement, position: Position) : Statement(position)