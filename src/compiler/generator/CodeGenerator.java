package compiler.generator;
import compiler.analysis.SemanticImpl;
import compiler.core.Expression;
import compiler.core.Type;
import compiler.core.Variable;
import compiler.exceptions.InvalidTypeException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

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
    private Stack<String> repeatLabels;

    public Integer repeatCount;

    private CodeGenerator(){
        registerCount = 0;
        lineCount = 100;
        stackCount = 600;
        repeatCount = 0;
        repeatLabels = new Stack<>();

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
        String command = "ST " + var.getIdentifier() + ", " + exp.getValue() + "\n";

        writeCommand(command);
    }

    public void generateLabelRepeat() {
        code += "repeat" + repeatCount + " : ";

        repeatLabels.push("repeat" + repeatCount);

        repeatCount += 1;
    }

    public void generateBranchToLabel(Expression exp){
        String regTrue = generateLoadVarToRegister("#0");

        writeCommand("BEQ " + exp.getValue()  + ", " +  regTrue + ", "+ ( lineCount + 16) + "\n");
        writeCommand("BR " + repeatLabels.pop() +"\n");
    }

    public Expression generateCommandByOp(String op, Expression exp1, Expression exp2) throws InvalidTypeException {

        String typeResu = Type.resultantType(exp1.getType(),exp2.getType());
        Expression result = new Expression(SemanticImpl.getInstance().getTypeById(typeResu));
        String register = allocateRegister();
        String selectOp = "";
        boolean isRelational = false;

        switch ( op ) {
            case "+":
                selectOp = "ADD";
                break;
            case "-":
                selectOp = "SUB";
                break;
            case "*":
                selectOp = "MUL";
                break;
            case "/":
                selectOp = "DIV";
                break;
            case ">":
                isRelational = true;
                writeCommand("SUB " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");
                writeCommand("BLTZ " + register + ", " + (lineCount + 24) + "\n");
                writeCommand("ADD " + register + ", #0, #1 \n");
                writeCommand("BR " + ( lineCount + 16) + "\n");
                writeCommand("ADD " + register + ", #0, #0  \n");
                break;
            case "=":
                isRelational = true;
                writeCommand("BEQ " + exp1.getValue()  + ", " +  exp2.getValue() + ", "+ ( lineCount + 24) + "\n");
                writeCommand("ADD " + register + ", #0, #0 \n");
                writeCommand("BR " + ( lineCount + 16) + "\n");
                writeCommand("ADD " + register + ", #0, #1  \n");
                break;
            case "<":
                isRelational = true;
                writeCommand("SUB " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");
                writeCommand("BGTZ " + register + ", " + (lineCount + 24) + "\n");
                writeCommand("ADD " + register + ", #0, #1 \n");
                writeCommand("BR " + ( lineCount + 16) + "\n");
                writeCommand("ADD " + register + ", #0, #0  \n");
                break;
            case ">=":
                isRelational = true;
                writeCommand("SUB " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");
                writeCommand("BLEZ " + register + ", " + (lineCount + 24) + "\n");
                writeCommand("ADD " + register + ", #0, #1 \n");
                writeCommand("BR " + ( lineCount + 16) + "\n");
                writeCommand("ADD " + register + ", #0, #0  \n");
                break;
            case "<=":
                isRelational = true;
                writeCommand("SUB  " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");
                writeCommand("BGEZ " + register + ", " + (lineCount + 24) + "\n");
                writeCommand("ADD  " + register + ", #0, #1 \n");
                writeCommand("BR   " + ( lineCount + 16) + "\n");
                writeCommand("ADD  " + register + ", #0, #0  \n");
                break;
            case "<>":
                isRelational = true;
                writeCommand("BNE " + exp1.getValue() + ", " + exp2.getValue() + ", " + (lineCount + 24) + "\n");
                writeCommand("ADD " + register + ", #0, #0 \n");
                writeCommand("BR  " + ( lineCount + 16) + "\n");
                writeCommand("ADD " + register + ", #0, #1  \n");
                break;
            default:
                break;
        }

        if (!isRelational) {
            writeCommand( selectOp + " " + register + ", " + exp1.getValue() + ", " + exp2.getValue() + "\n");
        } else {
            result.setType(SemanticImpl.getInstance().getTypeById("boolean"));
        }

        result.setValue(register);

        return result;
    }



    public String generateLoadValueToRegister(String value){
        String register = allocateRegister();

        writeCommand("LD " + register + ", #" + value + "\n");

        return register;
    }

    public String generateLoadVarToRegister(String value){
        String register = allocateRegister();

        writeCommand("LD " + register + ", " + value + "\n");

        return register;
    }

    public void generateHalt() {
      writeCommand("halt \n");
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
