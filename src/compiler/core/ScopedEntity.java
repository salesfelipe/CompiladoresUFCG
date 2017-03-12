package compiler.core;

import compiler.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopedEntity extends  NamedEntity {
    private Boolean isProcedure;
    private HashMap<String, Variable> variables;
    private HashMap<String, Type> types;
    private HashMap<String, String> identifiers;
    private HashMap<String,ScopedEntity> functionsAndProcedures;
    private List<Parameter> params;

    public ScopedEntity(String name) {
        super(name);
        variables = new HashMap<String, Variable>();
        types = new HashMap<String, Type>();
        identifiers = new HashMap<String, String>();
        functionsAndProcedures = new HashMap<String, ScopedEntity>();
    }

    public List<Parameter> getParams() {
        return params;
    }

    public void setParams(List<Parameter> params) {
        this.params = params;
    }

    public Boolean isProcedure() {
        return isProcedure;
    }

    public void setIsProcedure(Boolean procedure) {
        isProcedure = procedure;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public void addVariable(Variable v) throws InvalidNameException {
        this.addIdentifier(v.getIdentifier());
        this.variables.put(v.getIdentifier(), v);
    }
    
    public void addFunctionOrProcedure(ScopedEntity s) throws InvalidNameException {
    	this.addIdentifier(s.getName());
        this.functionsAndProcedures.put(s.getName(), s);
    }

    public void addIdentifier(String id) throws InvalidNameException {
        if(this.identifiers.containsKey(id)){
            throw new InvalidNameException("O nome: " + id + " Ja esta em uso no escopo atual!");
        }
        this.identifiers.put(id, id);
    }

    public void addType(Type t) {
        this.types.put(t.getName(), t);
    }

    public Map<String, Type> getTypes() {
        return types;
    }

    public HashMap<String, String> getIdentifiers() {
        return identifiers;
    }

    public HashMap<String, ScopedEntity> getFunctionsAndProcedures() {
        return functionsAndProcedures;
    }

    @Override
    public String toString() {
        return "ScopedEntity{" +
                "variables=" + variables + " id " + getName() +
                '}';
    }
}
