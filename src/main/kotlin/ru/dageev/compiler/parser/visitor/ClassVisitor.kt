package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import ru.dageev.compiler.parser.CompilationException
import ru.dageev.compiler.parser.helper.getDefaultConstructor
import ru.dageev.compiler.parser.helper.getDefaultConstructorSignature
import ru.dageev.compiler.parser.provider.TypeProvider
import java.util.*

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ClassVisitor(val typeProvider: TypeProvider, val classesContext: ClassesContext) : ElaginBaseVisitor<ClassDeclaration>() {
    lateinit var scope: Scope


    override fun visitClassDeclaration(@NotNull ctx: ElaginParser.ClassDeclarationContext): ClassDeclaration {
        val className = ctx.identifier().text


        val parent = getParentClass(ctx)
        scope = Scope(className, if (parent.isPresent) parent.get().name else null)

        val fields = processFields(ctx)
        registerMethodSignatures(ctx)
        val methods = ctx.classBody().methodDeclaration().map { method -> method.accept(MethodVisitor(scope, typeProvider, classesContext)) }
        val constructors = processConstructors(ctx)

        return ClassDeclaration(className, fields, methods, constructors, parent)
    }

    private fun getParentClass(ctx: ElaginParser.ClassDeclarationContext): Optional<ClassDeclaration> {
        val parentClassDeclaration = ctx.parentClassDeclaration()
        return if (parentClassDeclaration == null) {
            Optional.empty()
        } else {
            val classDeclaration = classesContext.classes[parentClassDeclaration.identifier().text] ?:
                    throw CompilationException("Parent class ${parentClassDeclaration.identifier().text} not exists for ${ctx.identifier().text}")
            Optional.of(classDeclaration)
        }
    }

    private fun processConstructors(ctx: ElaginParser.ClassDeclarationContext): List<MethodDeclaration.ConstructorDeclaration> {
        return if (ctx.classBody().constructorDeclaration().isEmpty()) {
            listOf(getDefaultConstructor(scope))
        } else {
            ctx.classBody().constructorDeclaration().map { constructor -> constructor.accept(ConstructorVisitor(scope, typeProvider, classesContext)) }
        }

    }


    private fun registerMethodSignatures(ctx: ElaginParser.ClassDeclarationContext) {
        val methodSignatureVisitor = MethodSignatureVisitor(scope, typeProvider, classesContext)

        ctx.classBody().constructorDeclaration().map { constructor -> constructor.accept(methodSignatureVisitor) }.forEach {
            scope.addConstructorSignature(it)
        }
        ctx.classBody().methodDeclaration().map { method -> method.accept(methodSignatureVisitor) }.forEach {
            scope.addSignature(it)
        }

        if (ctx.classBody().constructorDeclaration().isEmpty()) {
            scope.addConstructorSignature(getDefaultConstructorSignature(scope))
        }
    }

    private fun processFields(ctx: ElaginParser.ClassDeclarationContext): List<Field> {
        val fields = ctx.classBody().fieldDeclaration().map { field ->
            field.accept(FieldsVisitor(typeProvider, scope))
        }

        fields.forEach { scope.addField(it) }
        return fields
    }
}