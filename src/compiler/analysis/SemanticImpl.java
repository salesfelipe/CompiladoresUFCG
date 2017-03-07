package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.InvalidVariableException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class SemanticImpl {

    private static Stack<ScopedEntity> scopeStack;
    private static SemanticImpl singleton;
    private static HashMap<String, Variable> globalVariables;
    private static List<Variable> tempVariables;


    private static void initCollections() {
        globalVariables = new HashMap<String, Variable>();
        tempVariables = new ArrayList<Variable>();
        scopeStack = new Stack<ScopedEntity>();
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
    }

    public boolean checkVariableExistenceGlobal(String variableName) {
        return globalVariables.get(variableName) != null ? true : false;
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
        } else {
//            validateVariable(variable);
            getCurrentScope().addVariable(variable);
        }

//        if (variable.getValue() != null) {
//            checkVariableAttribution(variable.getIdentifier(),
//                    variable.getValue());
//        }
    }
}
