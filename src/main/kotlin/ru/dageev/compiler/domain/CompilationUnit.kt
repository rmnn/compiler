package ru.dageev.compiler.domain

import ru.dageev.compiler.domain.declaration.ClassDeclaration

/**
 * Created by dageev
 *  on 14-May-16.
 */
data class CompilationUnit(val classDeclarations: List<ClassDeclaration>)