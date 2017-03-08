package compiler.core;

import compiler.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.*;


public class Procedure extends ScopedEntity{
    private List<Parameter> params;
    private HashMap<String, String> identifiers;
    private static HashMap<String, Variable> variables;

    public Procedure(String name, ArrayList<Parameter> params) throws InvalidNameException {
        super(name);
        if(params != null){
            this.params = params;
            
        }else{
            this.params = new ArrayList<Parameter>();
        }
        
        initialize();
    }
    
    private void initialize() throws InvalidNameException {
    	for (int i = 0; i < params.size(); i++) {
			addVariable(new Variable(params.get(i).getType() ,params.get(i).getIdentifier(), false));
		}
    }

    public void setParams(List<Parameter> params) { this.params = params; }

    public List<Parameter> getParams() {
        return this.params;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Procedure)) return false;
        Procedure p = (Procedure) obj;
        if(!p.getName().equals(getName()))return false;
        if(p.getParams().size() != getParams().size()) return false;

        for(int i=0;i<getParams().size();i++){
            if(! p.getParams().get(i).getType().equals(getParams().get(i).getType())) return false;
        }

        return true;
    }
}
