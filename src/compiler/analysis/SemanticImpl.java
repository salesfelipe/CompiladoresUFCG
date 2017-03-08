package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidNameException;
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
    private static List<String> tempIdList;
    private static List<Parameter> tempParameters;

    private static void initCollections() {
        globalVariables = new HashMap<String, Variable>();
        tempVariables = new ArrayList<Variable>();
        scopeStack = new Stack<ScopedEntity>();
        functionsAndProcedures = new ArrayList<>();
        globalIdentifiers = new HashMap<>();
        tempIdList = new ArrayList<String>();
        tempParameters = new ArrayList<Parameter>();
    }

    public void addIdToTempList(String id) {
        tempIdList.add(id);
    }
    public void clearIdTempList() {
        System.out.println("CLEARRRRRR!!!!111");
        tempIdList.clear();
    }

    public void createParameters(Type type) {
        tempParameters.clear();
        for (int i = 0; i < tempIdList.size(); i++) {
            tempParameters.add(new DeclarationParameter(type,tempIdList.get(i)));
        }
        tempIdList.clear();
    }
    
    public ArrayList<Parameter> getParameters(){
    	return (ArrayList<Parameter>) tempParameters;
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
        for (String identifier: tempIdList) {
            addVariable(new Variable(type, identifier, false));
        }
        tempIdList.clear();
    }

    public void addVariableToTempList(Variable var) {
        tempVariables.add(var);
//        System.out.println("!!!!!!!!!!!!!!!!!"+Arrays.toString(tempVariables.toArray()));
    }

    public void cleanTempList() {
        tempVariables.clear();
    }


    public boolean checkIdentifierExistenceGlobal(String variableName) {
        return globalIdentifiers.get(variableName) != null ? true : false;
    }

    private void validateVariableGlobal(Variable variable) throws Exception {
        if (checkIdentifierExistenceGlobal(variable.getIdentifier())) {
            throw new InvalidVariableException("Name already exists");
        }
    }

    private void addVariable(Variable variable) throws Exception {
        if (scopeStack.isEmpty()) {
            validateVariableGlobal(variable);

            globalVariables.put(variable.getIdentifier(), variable);
            globalIdentifiers.put(variable.getIdentifier(), variable.getIdentifier());
        } else {
            getCurrentScope().addVariable(variable);
        }

    }

    public void addIdentifier(String id) throws InvalidNameException {
        if (globalIdentifiers.containsKey(id)) {
            throw new InvalidNameException("Id "+id+ " ja esta em uso.");
        }
        globalIdentifiers.put(id, id);
    }

    public void checkIdentifierExistence(String id) throws InvalidFunctionException {
        if (checkIdentifierExistenceGlobal(id)) {
            throw new InvalidFunctionException("ERROR: The function name '" + id + "' is already being used!");
        }
    }

    public void validateFunction(String functionName, ArrayList<Parameter> params, Type declaredType) throws InvalidFunctionException, InvalidParameterException, InvalidNameException {
        Function temp = new Function(functionName, params);
        checkIdentifierExistence(temp.getName());

        addFunctionProcedureAndNewScope(temp);
    }

    public void addFunctionProcedureAndNewScope(ScopedEntity f) throws InvalidNameException {
        if(scopeStack.isEmpty()){
        	addIdentifier(f.getName());
        	functionsAndProcedures.add(f);
        } else {
        	scopeStack.peek().addFunctionOrProcedure(f);
        }
        
        createNewScope(f);
    }
    
    public void exitScope() {
    	scopeStack.pop();
    }
    
}
