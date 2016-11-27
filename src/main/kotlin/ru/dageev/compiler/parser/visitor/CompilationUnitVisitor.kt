package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.getMainMethodSignature
import ru.dageev.compiler.parser.provider.TypeProvider

/**
 * Created by dageev
 *  on 15-May-16.
 */
class CompilationUnitVisitor : ElaginBaseVisitor<CompilationUnit>() {


    override fun visitCompilationUnit(@NotNull ctx: ElaginParser.CompilationUnitContext): CompilationUnit {
        val classesContext = ClassesContext()

        val typeProvider = createTypeProvider(ctx.classDeclaration())
        val classVisitor = ClassVisitor(typeProvider, classesContext)
        val classDeclarationContext = ctx.classDeclaration()
        val classes = classDeclarationContext.map { classDeclaration ->
            val classDecl = classDeclaration.accept(classVisitor)
            classesContext.addClass(classDecl)
            classDecl
        }.toMutableList()

        checkMainMethods(classesContext, classes)
        return CompilationUnit(classes)
    }

    private fun checkMainMethods(classesContext: ClassesContext, classes: MutableList<ClassDeclaration>) {
        val mainMethodSignature = getMainMethodSignature()
        val mainMethodCount = classesContext.getAllScopes().count { scope ->
            scope.signatureExists(mainMethodSignature)
        }
        if (mainMethodCount > 1) {
            throw CompilationException("Found more than 1 main method");
        }

        if (mainMethodCount == 0 && classesContext.classes.isNotEmpty()) {
            addStubMainMethod(classesContext, mainMethodSignature, classes)
        }
    }

    private fun addStubMainMethod(classesContext: ClassesContext, mainMethodSignature: MethodSignature, classes: MutableList<ClassDeclaration>) {
        val firstKey = classesContext.classes.keys.first()
        val classDecl = classesContext.classes[firstKey]!!
        val methods = classDecl.methods + MethodDeclaration(mainMethodSignature, Block(classesContext.getClassScope(firstKey), emptyList()))
        val patchedClassDeclaration = ClassDeclaration(classDecl.name, classDecl.fields, methods, classDecl.constructors, classDecl.parentClassDeclaration)
        classesContext.classes.put(firstKey, patchedClassDeclaration)

        classes.remove(classes.find { it.name == firstKey })
        classes.add(patchedClassDeclaration)
    }

    private fun createTypeProvider(classDeclarationContext: List<ElaginParser.ClassDeclarationContext>): TypeProvider {
        return TypeProvider(classDeclarationContext.map { ClassType(it.identifier().text) })
    }
}
