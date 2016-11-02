package ru.dageev.compiler.parser.visitor

import org.antlr.v4.runtime.misc.NotNull
import ru.dageev.compiler.domain.AccessModifier
import ru.dageev.compiler.domain.ClassesContext
import ru.dageev.compiler.domain.declaration.ClassDeclaration
import ru.dageev.compiler.domain.declaration.MethodDeclaration
import ru.dageev.compiler.domain.node.statement.Block
import ru.dageev.compiler.domain.scope.Field
import ru.dageev.compiler.domain.scope.MethodSignature
import ru.dageev.compiler.domain.scope.Scope
import ru.dageev.compiler.domain.type.ClassType
import ru.dageev.compiler.grammar.ElaginBaseVisitor
import ru.dageev.compiler.grammar.ElaginParser
import java.util.*

/**
 * Created by dageev
 *  on 15-May-16.
 */
class ClassVisitor(val classesContext: ClassesContext) : ElaginBaseVisitor<ClassDeclaration>() {
    lateinit var scope: Scope


    override fun visitClassDeclaration(@NotNull ctx: ElaginParser.ClassDeclarationContext): ClassDeclaration {
        val className = ctx.Identifier().text
        scope = Scope(className)


        val parent = getParentClass(ctx)
        val fields = processFields(ctx)
        registerMethodSignatures(ctx)
        val methods = ctx.classBody().methodDeclaration().map { method -> method.accept(MethodVisitor(scope, classesContext)) }
        val constructors = processConstructors(ctx)

        return ClassDeclaration(className, fields, methods, constructors, parent)
    }

    private fun getParentClass(ctx: ElaginParser.ClassDeclarationContext): Optional<ClassDeclaration> {
        val parentClassDeclaration = ctx.parentClassDeclaration()
        return if (parentClassDeclaration == null) {
            Optional.empty()
        } else {
            val parentClass = classesContext.classes[parentClassDeclaration.Identifier().text]
            if (parentClass == null) {
                // TODO error handling
                Optional.empty()
            } else {
                Optional.of(parentClass)
            }
        }
    }

    private fun processConstructors(ctx: ElaginParser.ClassDeclarationContext): List<MethodDeclaration.ConstructorDeclaration> {
        return if (ctx.classBody().constructorDeclaration().isEmpty()) {
            listOf(getDefaultConstructor())
        } else {
            ctx.classBody().constructorDeclaration().map { constructor -> constructor.accept(ConstructorVisitor(scope, classesContext)) }
        }

    }

    private fun getDefaultConstructor(): MethodDeclaration.ConstructorDeclaration {
        return MethodDeclaration.ConstructorDeclaration(AccessModifier.PUBLIC, getDefaultConstructorSignature(), Block(scope, emptyList()))
    }

    private fun registerMethodSignatures(ctx: ElaginParser.ClassDeclarationContext) {
        val methodSignatureVisitor = MethodSignatureVisitor(scope, classesContext)

        ctx.classBody().methodDeclaration().map { method -> method.accept(methodSignatureVisitor) }.forEach {
            scope.addSignature(it)
        }
        ctx.classBody().constructorDeclaration().map { constructor -> constructor.accept(methodSignatureVisitor) }.forEach {
            scope.addSignature(it)
        }
        if (ctx.classBody().constructorDeclaration().isEmpty()) {
            scope.addSignature(getDefaultConstructorSignature())
        }
    }

    private fun getDefaultConstructorSignature(): MethodSignature {
        return MethodSignature(scope.className, emptyList(), ClassType(scope.className))
    }


    private fun processFields(ctx: ElaginParser.ClassDeclarationContext): List<Field> {
        val fields = ctx.classBody().fieldDeclaration().map { field ->
            field.accept(FieldsVisitor(scope))
        }

        fields.forEach { scope.addField(it) }
        return fields
    }
}