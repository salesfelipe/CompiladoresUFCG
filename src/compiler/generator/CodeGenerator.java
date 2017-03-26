package compiler.generator;
import compiler.core.Register;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by felipesales on 26/03/17.
 */
public class CodeGenerator
{
    private static CodeGenerator singleton;

    private String fileName = "out.asm";
    private String code;

    public Integer registerCount;
    public Integer stackCount;
    public Integer lineCount;

    private static Register[] registers;

    private CodeGenerator(){
        registerCount = 0;
        lineCount = 100;
        stackCount = 600;

        initAssemblyCode();
    }

    public static CodeGenerator getInstance() {
        if(singleton == null) {
            singleton = new CodeGenerator();
        }

        return singleton;
    }

    private void initAssemblyCode() {
        code = "";

        code += lineCount + ": LD SP, # " + stackCount + "\n";

        incrementLineCount();
    }

    private void incrementLineCount() {
        lineCount += 8;
    }

    public void generateFinalAssemblyCode() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        writer.write(code);
        writer.close();
    }



}
