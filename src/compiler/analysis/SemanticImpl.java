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
    private static HashMap<String, ScopedEntity> functionsAndProcedures;
    private static Set<String> globalIdentifiers;
    private static List<String> tempIdList;
    private static List<Parameter> tempParameters;
    private static String selectedId;

    private static void initCollections() {
        globalVariables = new HashMap<String, Variable>();
        scopeStack = new Stack<ScopedEntity>();
        functionsAndProcedures = new HashMap<String, ScopedEntity>();
        globalIdentifiers = new HashSet<String>();
        tempIdList = new ArrayList<String>();
        tempParameters = new ArrayList<Parameter>();
    }

    public void addIdToTempList(String id) {
        tempIdList.add(id);
    }

    public void clearIdTempList() {
        tempIdList.clear();
    }

    public void addToTempParameter(Parameter param) {
        tempParameters.add(param);
    }

    public void clearParameterList() {
        tempParameters.clear();
    }

    public void createParameters(Type type) {
        tempParameters.clear();
        for (int i = 0; i < tempIdList.size(); i++) {
            tempParameters.add(new DeclarationParameter(type, tempIdList.get(i)));
        }
        tempIdList.clear();
    }

    public ArrayList<Parameter> getParameters() {
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
        for (String identifier : tempIdList) {
            addVariable(new Variable(type, identifier, false));
        }
        tempIdList.clear();
    }

    public void addVariable(Variable variable) throws InvalidNameException {
        if (scopeStack.isEmpty()) {
            addIdentifier(variable.getIdentifier());
            globalVariables.put(variable.getIdentifier(), variable);

        } else {
            getCurrentScope().addVariable(variable);
        }

    }

    public void validateFunction(Expression e, String id) throws InvalidFunctionException {
        ((Function) functionsAndProcedures.get(id)).validateReturnedType(e.getType());
        if (!scopeStack.isEmpty()) {
            exitCurrentScope();
        }
    }

    public void setReturnType(String id, Type type) {
        ((Function) functionsAndProcedures.get(id)).setDeclaredReturnedType(type);
    }

    public Expression getTypeByID(String id) throws InvalidVariableException {
        Type result;
        if (globalVariables.containsKey(id)) {
            result = globalVariables.get(id).getType();
        }
        else if (functionsAndProcedures.containsKey(id)) {
            result = ((Function) functionsAndProcedures.get(id)).getDeclaredReturnType();
        }
        else if (scopeStack.peek().getVariables().containsKey(id)) {
            result = scopeStack.peek().getVariables().get(id).getType();
        }
        else {
            throw new InvalidVariableException("A variável " + id +" nunca foi declarada.");
        }
        return new Expression(result, null);
    }

    private void addIdentifier(String id) throws InvalidNameException {
        if (globalIdentifiers.contains(id)) {
            throw new InvalidNameException("Id " + id + " ja esta em uso.");
        }
        globalIdentifiers.add(id);
    }

    public void addFunctionProcedureAndNewScope(ScopedEntity f) throws InvalidNameException {
        if (scopeStack.isEmpty()) {
            addIdentifier(f.getName());
            functionsAndProcedures.put(f.getName(), f);
        } else {
            scopeStack.peek().addFunctionOrProcedure(f);
        }

        createNewScope(f);
    }

    public void exitCurrentScope() {
        if (!scopeStack.isEmpty())
            scopeStack.pop();
    }

    public void setSelectedId(String id) {
        selectedId = id;
    }

    public void checkFunctionCall() {
        
    };

}
