package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
class CompilationUnitVisitor : ElaginBaseVisitor<CompilationUnit>() {

    override fun visitCompilationUnit(@NotNull ctx: ElaginParser.CompilationUnitContext): CompilationUnit {
        val classVisitor = ClassVisitor()
        val classDeclarationContext = ctx.classDeclaration()
        val classes = classDeclarationContext.map { classDeclaration ->
            classDeclaration.accept(classVisitor)
        }
        return CompilationUnit(classes)
    }
}
