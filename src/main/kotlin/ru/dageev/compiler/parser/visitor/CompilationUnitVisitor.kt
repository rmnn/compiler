package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser

/**
 * Created by dageev
 *  on 15-May-16.
 */
class CompilationUnitVisitor : ElaginBaseVisitor<CompilationUnit>() {


    override fun visitCompilationUnit(@NotNull ctx: ElaginParser.CompilationUnitContext): CompilationUnit {
        val classesContext = ClassesContext()

        val classVisitor = ClassVisitor(classesContext)
        val classDeclarationContext = ctx.classDeclaration()
        val classes = classDeclarationContext.map { classDeclaration ->
            val classDecl = classDeclaration.accept(classVisitor)
            classesContext.addClass(classDecl)
            classDecl
        }
        return CompilationUnit(classes)
    }
}
