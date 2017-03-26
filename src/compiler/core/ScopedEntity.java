package compiler.core;

import compiler.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScopedEntity {
    private String name;
    private Boolean isProcedure;
    private Map<String, Variable> variables;
    private Map<String, Type> types;
    private Map<String, ScopedEntity> functionsAndProcedures;
    private List<Parameter> params;

    public ScopedEntity(String name) {
        this.name = name.toLowerCase();
        variables = new HashMap<String, Variable>();
        types = new HashMap<String, Type>();
        functionsAndProcedures = new HashMap<String, ScopedEntity>();
    }

    protected void initialize() throws InvalidNameException {
        Map<String, Variable> temp = new HashMap<>();

        Parameter p;
        for (int i = 0; i < getParams().size(); i++) {
            p = getParams().get(i);
            if (temp.containsKey(p.getIdentifier())) {
                throw new InvalidNameException("Já existe um parametro com o nome: " + p.getIdentifier());
            }

            temp.put(p.getIdentifier(), new Variable(p.getType(), p.getIdentifier(), false));
        }

        addVariables(temp);
    }


    public Variable getVariable(String id) {
        return variables.get(id);
    }

    public ScopedEntity getFunctionOrProcedure(String id) {
        return functionsAndProcedures.get(id);
    }

    public boolean isIdBeenUsed(String id) {
        return variables.containsKey(id) || functionsAndProcedures.containsKey(id) || types.containsKey(id);
    }

    public boolean existsVariable(String id) {
        return variables.containsKey(id.toLowerCase());
    }

    public boolean existsFunctionOrProcedure(String id) {
        return functionsAndProcedures.containsKey(id);
    }

    public String getName() {
        return name;
    }

    public List<Parameter> getParams() {
        return params;
    }

    public Boolean isProcedure() {
        return isProcedure;
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Map<String, Type> getTypes() {
        return types;
    }

    public Map<String, ScopedEntity> getFunctionsAndProcedures() {
        return functionsAndProcedures;
    }

    public void setParams(List<Parameter> params) {
        this.params = params;
    }

    public void setIsProcedure(Boolean procedure) {
        isProcedure = procedure;
    }

    public void addVariable(Variable v) throws InvalidNameException {
        String id = v.getIdentifier();

        if (variables.containsKey(id)) {
            throw new InvalidNameException("Já existe uma variável com o nome: " + id + " no escopo atual!");
        }

        this.variables.put(id, v);
    }

    public void addFunctionOrProcedure(ScopedEntity s) throws InvalidNameException {
        String id = s.getName();

        if (variables.containsKey(id)) {
            throw new InvalidNameException("Já existe uma função/procedure com o nome: " + id + " no escopo atual!");
        }

        this.functionsAndProcedures.put(id, s);
    }

    public void addType(Type t) throws InvalidNameException {
        String id = t.getName();

        if (types.containsKey(id)) {
            throw new InvalidNameException("Já existe um tipo com o nome: " + id + " no escopo atual!");
        }

        types.put(id, t);
    }

    public void addVariables(Map<String, Variable> vars) {
        for (String key : vars.keySet()) {
            if (!this.variables.containsKey(key)) {
                this.variables.put(key, vars.get(key));
            }
        }
    }

    public void addFunctionsAndProcedures(Map<String, ScopedEntity> functionsAndProcedures) {
        for (String key : functionsAndProcedures.keySet()) {
            if (!this.functionsAndProcedures.containsKey(key)) {
                this.functionsAndProcedures.put(key, functionsAndProcedures.get(key));
            }
        }
    }

    public void addTypes(Map<String, Type> types) {
        for (String key : types.keySet()) {
            if (!this.types.containsKey(key)) {
                this.types.put(key, types.get(key));
            }
        }
    }


    @Override
    public String toString() {
        return "Scopo : " + name + ";" +
                "Variables : " + variables + " ;";
    }
}
