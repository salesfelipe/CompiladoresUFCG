package compiler.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScopedEntity extends  NamedEntity {
    private HashMap<String, Variable> variables;
    private HashMap<String, Type> types;
    private HashMap<String, String> identifiers;
    private HashMap<String,ScopedEntity> functionsAndProcedures;

    public ScopedEntity(String name) {
        super(name);
        variables = new HashMap<String, Variable>();
        types = new HashMap<String, Type>();
        identifiers = new HashMap<String, String>();
        functionsAndProcedures = new HashMap<String, ScopedEntity>();
    }

    public Map<String, Variable> getVariable() {
        return variables;
    }

    public void addVariable(Variable v) {
        this.variables.put(v.getIdentifier(), v);
    }
    
    public void addFunctionOrProcedure(ScopedEntity s) {
    	this.functionsAndProcedures.put(s.getName(), s);
    }
    
    public void addIdentifier(String id) {
        this.identifiers.put(id, id);
    }

    public void addType(Type t) {
        this.types.put(t.getName(), t);
    }

    public Map<String, Type> getTypes() {
        return types;
    }
}
