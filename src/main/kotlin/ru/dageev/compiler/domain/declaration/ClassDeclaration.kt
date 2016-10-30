package ru.dageev.compiler.domain.declaration

/**
 * Created by dageev
 *  on 14-May-16.
 */
data class ClassDeclaration(val name: String, val fields: List<VariableDeclaration.Field>, val methods: List<MethodDeclaration>,
                            val constructors: List<MethodDeclaration.ConstructorDeclaration>)