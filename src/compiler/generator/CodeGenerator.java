package compiler.generator;
import compiler.analysis.SemanticImpl;
import compiler.core.Expression;
import compiler.core.Register;
import compiler.core.Type;
import compiler.core.Variable;
import compiler.exceptions.InvalidTypeException;

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

        writeCommand("LD SP, # " + stackCount + "\n");
    }

    private void writeCommand(String command) {
        code +=  lineCount + ": " + command;
        lineCount += 8;
    }

    public void generateAssignment(Variable var, Expression exp) {
        String command = "ST " + var.getIdentifier() + ", " + exp.getValue();

        writeCommand(command);
    }

    public Expression generateCommandByOp(String op, Expression exp1, Expression exp2) throws InvalidTypeException {

        String typeResu = Type.resultantType(exp1.getType(),exp2.getType());
        Expression result = new Expression(SemanticImpl.getInstance().getTypeById(typeResu));
        String register = allocateRegister();
        String selectOp = "";

        switch ( op ) {
            case "+":
                selectOp = "ADD";
                break;
            case "-":
                selectOp = "SUB";
                break;
            case "*":
                selectOp = "MUL";
            default:
                break;
        }

        writeCommand( selectOp + " " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");

        result.setValue(register);

        if(op.equalsIgnoreCase("+")){

        }

        return result;
    }

    public String generateLoadValueToRegister(String value){
        String register = allocateRegister();

        writeCommand("LD " + register + ", #" + value + "\n");

        return register;
    }

    private String allocateRegister(){
        String register = "r" + registerCount;

        registerCount++;

        return register;
    }

    public void generateFinalAssemblyCode() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));
        writer.write(code);
        writer.close();
    }



}
