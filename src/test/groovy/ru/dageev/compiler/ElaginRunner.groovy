package ru.dageev.compiler

/**
 * Created by dageev 
 * on 12/11/16.
 */
class ElaginRunner {
    String tempDir = "java.io.tmpdir"

    Compiler compiler = new Compiler()


    String run(String source) {
        String path = createElgFile(source)
        String[] args = [path]
        compiler.compile(args)
        File elaginProgram = new File("target/ElaginProgram.class")
        assert elaginProgram.exists()

        def sout = new StringBuilder(), serr = new StringBuilder()
        def proc = "java -cp target ElaginProgram".execute()
        proc.consumeProcessOutput(sout, serr)
        proc.waitForOrKill(1000)
        return sout.toString().trim() + serr.toString().trim()

    }

    private String createElgFile(String source) {
        File tempDir = getTempDir()
        File sourceFile = new File(tempDir, "elagin.elg")
        sourceFile.write(source)
        sourceFile.absolutePath
    }


    File getTempDir() {
        return new File(System.getProperty(tempDir))
    }
}
