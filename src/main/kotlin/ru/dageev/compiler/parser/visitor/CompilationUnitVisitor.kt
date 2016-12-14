package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.CompilationUnit
import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.helper.getDefaultConstructor
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

        val classDeclaration = processMethodWithoutClass(typeProvider, classesContext, ctx)
        return CompilationUnit(classes, classDeclaration)
    }

    fun processMethodWithoutClass(typeProvider: TypeProvider, classesContext: ClassesContext, ctx: ElaginParser.CompilationUnitContext): ClassDeclaration {
        val scope = Scope("ElaginProgram", null)
        val methods = getMethods(classesContext, ctx, scope, typeProvider)

        val classDeclaration = ClassDeclaration("ElaginProgram", emptyList(), methods, listOf(getDefaultConstructor(scope)))
        return checkAndPatchForMainMethodMainClass(scope, classesContext, classDeclaration)
    }

    private fun getMethods(classesContext: ClassesContext, ctx: ElaginParser.CompilationUnitContext, scope: Scope, typeProvider: TypeProvider): List<MethodDeclaration> {
        return if (ctx.methodDeclaration() != null) {
            ctx.methodDeclaration().map { method -> method.accept(MethodSignatureVisitor(scope, typeProvider, classesContext)) }.forEach {
                scope.addSignature(it)
            }

            ctx.methodDeclaration().map { method -> method.accept(MethodVisitor(scope, typeProvider, classesContext)) }
        } else {
            emptyList()
        }
    }

    private fun checkAndPatchForMainMethodMainClass(scope: Scope, classesContext: ClassesContext, classDeclaration: ClassDeclaration): ClassDeclaration {
        val mainMethodSignature = getMainMethodSignature()
        val mainMethodExists = classesContext.toScope(classDeclaration).signatureExists(mainMethodSignature)
        return if (!mainMethodExists) {
            addStubMainMethod(mainMethodSignature, scope, classDeclaration)
        } else {
            classDeclaration
        }
    }

    private fun addStubMainMethod(mainMethodSignature: MethodSignature, scope: Scope, classDecl: ClassDeclaration): ClassDeclaration {
        val methods = classDecl.methods + MethodDeclaration(mainMethodSignature, Block(scope, emptyList()))
        return ClassDeclaration(classDecl.name, classDecl.fields, methods, classDecl.constructors, classDecl.parentClassDeclaration)
    }

    private fun createTypeProvider(classDeclarationContext: List<ElaginParser.ClassDeclarationContext>): TypeProvider {
        return TypeProvider(classDeclarationContext.map { ClassType(it.identifier().text) })
    }
}
