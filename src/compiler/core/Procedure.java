package compiler.core;

import compiler.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.*;


public class Procedure extends ScopedEntity{
    private HashMap<String, String> identifiers;
    private static HashMap<String, Variable> variables;

    public Procedure(String name, ArrayList<Parameter> params) throws InvalidNameException {
        super(name);
        if(params != null){
            setParams(params);
        }else{
            setParams(new ArrayList<Parameter>());
        }
        setIsProcedure(true);
        initialize();
    }
    
    private void initialize() throws InvalidNameException {
    	for (int i = 0; i < getParams().size(); i++) {
			addVariable(new Variable(getParams().get(i).getType() ,getParams().get(i).getIdentifier(), false));
		}
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
