package ru.dageev.compiler.bytecodegeneration

import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.domain.declaration.ClassDeclaration


/**
 * Created by dageev
 * on 11/26/16.
 */
class CompilationUnitGenerator() {

    fun generate(compilationUnit: CompilationUnit): Map<String, ByteArray> {
        val classesContext = ClassesContext(compilationUnit.classDeclarations.map { it.name to it }.toMap() as MutableMap<String, ClassDeclaration>)
        val classGenerator = ClassGenerator(classesContext)
        val mainProgramClass = compilationUnit.mainClassDeclaration.name to classGenerator.generate(compilationUnit.mainClassDeclaration, true)
        return (compilationUnit.classDeclarations.map { it.name to classGenerator.generate(it) } + mainProgramClass).toMap()
    }
}