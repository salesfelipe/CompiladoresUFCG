package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidParameterException;
import compiler.exceptions.InvalidVariableException;

import java.util.*;

public class SemanticImpl {

    private static Stack<ScopedEntity> scopeStack;

    private static SemanticImpl singleton;
    private static HashMap<String, Variable> globalVariables;
    private static ArrayList<ScopedEntity> functionsAndProcedures;
    private static List<Variable> tempVariables;
    private static HashMap<String, String> globalIdentifiers;


    private static void initCollections() {
        globalVariables = new HashMap<String, Variable>();
        tempVariables = new ArrayList<Variable>();
        scopeStack = new Stack<ScopedEntity>();
        functionsAndProcedures = new ArrayList<>();
        globalIdentifiers = new HashMap<>();
    }

    public static SemanticImpl getInstance() {
        if (singleton == null) {
            singleton = new SemanticImpl();
            initCollections();
        }

        return singleton;
    }

    private void createNewScope(ScopedEntity scope) {
        scopeStack.push(scope);
    }

    public ScopedEntity getCurrentScope() {
        return scopeStack.peek();
    }

    public void addVariablesFromTempList(Type type) throws Exception {
        for (Variable variable : tempVariables) {
            variable.setType(type);
            addVariable(variable);

        }

        tempVariables = new ArrayList<Variable>();
    }

    public void addVariableToTempList(Variable var) {
        tempVariables.add(var);
//        System.out.println("!!!!!!!!!!!!!!!!!"+Arrays.toString(tempVariables.toArray()));
    }

    public void cleanTempList() {
        tempVariables.clear();
    }

    public void printTempList() {
        System.out.println("!!!!!!!!!!!!!!!!!"+Arrays.toString(tempVariables.toArray()));
    }

    public boolean checkVariableExistenceGlobal(String variableName) {
        return globalVariables.get(variableName) != null ? true : false;
    }

    public boolean checkIdentifierExistenceGlobal(String variableName) {
        return globalIdentifiers.get(variableName) != null ? true : false;
    }

    private void validateVariableGlobal(Variable variable) throws Exception {
        if (checkVariableExistenceGlobal(variable.getIdentifier())) {
            throw new InvalidVariableException("Name already exists");
        }
//        if (!checkValidExistingType(variable.getType())) {
//            throw new InvalidTypeException("Type non existing");
//        }
    }

    private void addVariable(Variable variable) throws Exception {
        if (scopeStack.isEmpty()) {
            validateVariableGlobal(variable);

            globalVariables.put(variable.getIdentifier(), variable);
            globalIdentifiers.put(variable.getIdentifier(), variable.getIdentifier());
        } else {
//            validateVariable(variable);
            getCurrentScope().addVariable(variable);
        }

//        if (variable.getValue() != null) {
//            checkVariableAttribution(variable.getIdentifier(),
//                    variable.getValue());
//        }
    }

    public void checkIdentifierExistence(String id) throws InvalidFunctionException {
        if (checkIdentifierExistenceGlobal(id)) {
            throw new InvalidFunctionException("ERROR: The function name '" + id + "' is already being used!");
        }
    }

    public void validateFunction(String functionName, ArrayList<Parameter> params, Type declaredType) throws InvalidFunctionException, InvalidParameterException {
//        if (declaredType == null) {
//            throw new InvalidFunctionException(
//                    "The function "
//                            + functionName
//                            + " is missing either a declared return type or a return statement in the end of it");
//        }
        Function temp = new Function(functionName, params);
        checkIdentifierExistence(temp.getName());

//        if (params != null) {
//            for (Parameter p : params) {
//                variables.put(p.getIdentifier(), (Variable) p);
//            }
////            checkExistingParameter(params);
//        }
//        temp.setDeclaredReturnedType(declaredType);
        addFunctionAndNewScope(temp);
    }

    public void addFunctionAndNewScope(Function f) {
        functionsAndProcedures.add(f);
        globalIdentifiers.put(f.getName(), f.getName());
        createNewScope(f);
    }
}
