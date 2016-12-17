package ru.dageev.compiler

import org.slf4j.LoggerFactory
import ru.dageev.compiler.bytecodegeneration.CompilationUnitGenerator
import ru.dageev.compiler.parser.Parser
import java.io.File

/**
 * Created by dageev
 * on 11/26/16.
 */


class Compiler {

    companion object {
        val logger = LoggerFactory.getLogger(Compiler::class.java)
        val parser = Parser()
        val byteCodeGenerator = CompilationUnitGenerator()
    }

    fun compile(args: Array<String>) {
        if (!correctArgs(args)) {
            return
        }

        val program = parser.parse(args[0])
        logger.info("Successfully parsed from ${args[0]}")

        val targetDir = createTargetDir()
        val classToByteCodeMap = byteCodeGenerator.generate(program)
        classToByteCodeMap.forEach { classToByteCode ->
            val classFile = File(targetDir, classToByteCode.key + ".class")
            classFile.writeBytes(classToByteCode.value)
        }
        logger.info("Successfully compiled ${classToByteCodeMap.size} classes to ${targetDir.absolutePath}")
    }

    private fun createTargetDir(): File {
        val targetDir = File("target")
        if (!targetDir.exists()) {
            targetDir.mkdirs()
        }
        return targetDir
    }

    private fun correctArgs(args: Array<String>): Boolean {
        if (args.isEmpty()) {
            logger.error("Please specify file to be compiled")
            return false
        }

        if (args.size > 1) {
            logger.error("Arguments should contains only 1 file to be compiled")
            return false
        }

        val filePath = args[0]
        if (!filePath.endsWith(suffix = ".elg")) {
            logger.error("Incorrect file format. Should be *.elg")
            return false
        }
        return true
    }
}


fun main(args: Array<String>) {
    Compiler().compile(args)
}