package compiler.core;

import compiler.exceptions.InvalidNameException;

import java.util.List;
import java.util.Map;
import java.util.Stack;


/**
 * Created by felipesales on 13/03/17.
 */
public class ScopedEntityRepository {

    private static final String GLOBAL_SCOPE_NAME = "global-scope";


    private static ScopedEntityRepository singleton;

    private static Stack<ScopedEntity> scopeStack;

    private static void initiate() {
        scopeStack = new Stack<ScopedEntity>();
        initiateGlobalScope();
    }

    private ScopedEntityRepository(){}

    public static ScopedEntityRepository getInstance() {
        if (singleton == null) {
            singleton = new ScopedEntityRepository();
            initiate();
        }

        return singleton;
    }

    private static void initiateGlobalScope() {
        ScopedEntity global = new ScopedEntity(GLOBAL_SCOPE_NAME);

        List<Type> primitiveTypes = Type.getPrimitiveTypes();

        for (Type type : primitiveTypes ) {
            try {
                global.addType(type);
            } catch (InvalidNameException e) {
                e.printStackTrace();
            }
        }

        scopeStack.push(global);
    }

    private void createNewScope(ScopedEntity scope) {
        Map<String, Variable> vars = scopeStack.peek().getVariables();
        Map<String, ScopedEntity> functionsAndProcedures = scopeStack.peek().getFunctionsAndProcedures();
        Map<String, Type> types = scopeStack.peek().getTypes();

        scope.addVariables(vars);
        scope.addFunctionsAndProcedures(functionsAndProcedures);
        scope.addTypes(types);

        scopeStack.push(scope);
    }

    public void addFunctionOrProcedure(ScopedEntity s) throws InvalidNameException {

        scopeStack.peek().addFunctionOrProcedure(s);

        createNewScope(s);
    }

    public void addVariable(Variable variable) throws InvalidNameException {
        scopeStack.peek().addVariable(variable);
    }

    public ScopedEntity getFunctionOrProcedure(String id) {
        return scopeStack.peek().getFunctionOrProcedure(id);
    }

    public Variable getVariable(String id) {
        return scopeStack.peek().getVariable(id);
    }

    public boolean existsVariable(String id) {
        return scopeStack.peek().existsVariable(id);
    }

    public boolean existsFunctionOrProcedure(String id) {
        return scopeStack.peek().existsFunctionOrProcedure(id);
    }

    public boolean isEmpty(){
        return scopeStack.peek().getName().equals(GLOBAL_SCOPE_NAME);
    }

    public void exitCurrentScope() {
        if (isEmpty()) {
            scopeStack.pop();
        }
    }
}
