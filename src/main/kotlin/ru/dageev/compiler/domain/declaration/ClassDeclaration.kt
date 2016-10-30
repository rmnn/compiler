package ru.dageev.compiler.domain.declaration

import ru.dageev.compiler.domain.scope.Field
import java.util.*

/**
 * Created by dageev
 *  on 14-May-16.
 */
data class ClassDeclaration(val name: String, val fields: List<Field>, val methods: List<MethodDeclaration>,
                            val constructors: List<MethodDeclaration.ConstructorDeclaration>, val parentClassDeclaration: Optional<ClassDeclaration> = Optional.empty())