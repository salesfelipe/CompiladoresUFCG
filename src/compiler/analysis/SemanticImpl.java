package compiler.analysis;

import compiler.core.*;
import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidNameException;
import compiler.exceptions.InvalidParameterException;
import compiler.exceptions.InvalidVariableException;

import java.util.*;

public class SemanticImpl {

    private static ArrayList<Type> BASIC_TYPES;
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
        initBasicTypes();
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
//        tempParameters.clear();
        for (int i = 0; i < tempIdList.size(); i++) {
            tempParameters.add(new DeclarationParameter(type, tempIdList.get(i)));
        }
//        tempIdList.clear();
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
            if (!functionsAndProcedures.get(id).isProcedure())
                result = ((Function) functionsAndProcedures.get(id)).getDeclaredReturnType();
            else
                result = new Type("void");
        }
        else if (scopeStack.peek().getIdentifiers().containsKey(id)) {
            if (scopeStack.peek().getVariables().containsKey(id)) {
                result = scopeStack.peek().getVariables().get(id).getType();
            }
            else {
                result = ((Function)scopeStack.peek().getFunctionsAndProcedures().get(id)).getDeclaredReturnType();
            }
        }
        else {
            throw new InvalidVariableException("A variável " + id +" nunca foi declarada.");
        }
        return new Expression(result, id);
    }

    private Boolean isScopedEntity(String id ) {
        return functionsAndProcedures.containsKey(id);
    }

    private void addIdentifier(String id) throws InvalidNameException {
        if (!isScopedEntity(id) && globalIdentifiers.contains(id)) {
            throw new InvalidNameException("Id " + id + " ja esta em uso.");
        }
        else {

            globalIdentifiers.add(id);
        }
    }

    public void addFunctionProcedureAndNewScope(ScopedEntity f) throws InvalidNameException {
//        System.out.println("addFunctionProcedureAndNewScope:"+f.getParams());
        List<Parameter> parametros = new ArrayList<Parameter>(f.getParams());
        f.setParams(parametros);
        if (scopeStack.isEmpty()) {
            addIdentifier(f.getName().toLowerCase());
            functionsAndProcedures.put(f.getName().toLowerCase(), f);
        } else {
            scopeStack.peek().addFunctionOrProcedure(f);
        }

        createNewScope(f);
    }

    public void exitCurrentScope() {
        if (!scopeStack.isEmpty()) {
            scopeStack.pop();
            tempIdList.clear();
            tempParameters.clear();
        }
    }

    public void setSelectedId(String id) {
        selectedId = id;
    }

    public void checkFunctionCall() throws InvalidParameterException, InvalidFunctionException {
        selectedId = selectedId.toLowerCase();
        if (!functionsAndProcedures.containsKey(selectedId))
            throw new InvalidFunctionException("A função '" + selectedId + "' não existe");

        List<Parameter> parametrosFuncao = functionsAndProcedures.get(selectedId).getParams();
        List<Parameter> parametrosChamada = tempParameters;
        if (parametrosChamada.size() != parametrosFuncao.size())
            throw new InvalidParameterException("A quantidade de parâmetros da função "+selectedId+" está incorreta.");
        for (int i = 0 ; i < parametrosChamada.size(); i++) {
            if (!parametrosFuncao.get(i).equals(parametrosChamada.get(i))) {
                throw new InvalidParameterException("O parâmetro: '"+parametrosChamada.get(i)+" deveria ser do tipo "+parametrosFuncao.get(i).getType());
            }
        }

    };

    public boolean checkValidExistingType(Type type) {
        return BASIC_TYPES.contains(type);
    }

    private static void initBasicTypes() {
        BASIC_TYPES = new ArrayList<Type>() {
            {
                add(new Type("integer"));
                add(new Type("string"));
                add(new Type("char"));
                add(new Type("real"));
                add(new Type("boolean"));
                add(new Type("nil"));
                add(new Type("set"));
            }
        };
    }

}
