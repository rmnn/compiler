package ru.dageev.compiler.bytecodegeneration

import ru.dageev.compiler.domain.CompilationUnit


/**
 * Created by dageev
 * on 11/26/16.
 */
class CompilationUnitGenerator {

    fun generate(compilationUnit: CompilationUnit): Map<String, ByteArray> {
        val classGenerator = ClassGenerator()
        return compilationUnit.classDeclarations.map { it.name to classGenerator.generate(it) }.toMap()
    }
}