package compiler.core;

import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidNameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function extends ScopedEntity{

    private Type declaredReturnType;
    private List<Parameter> params;

    public Function(String name, ArrayList<Parameter> params) throws InvalidNameException {
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
    
    public Type getDeclaredReturnType() {
        return declaredReturnType;
    }

    public List<Parameter> getParams() {
        return this.params;
    }

    public void setDeclaredReturnedType(Type type) {
        this.declaredReturnType = type;
    }

    public void validateReturnedType(Type returnType) throws InvalidFunctionException { // Checks if the function returned what it was supposed to..
        System.out.println(returnType.getName());
        System.out.println(declaredReturnType.getName());
        if (!returnType.equals(declaredReturnType))
            throw new InvalidFunctionException("Function " + getName() + " was supposed to return " + declaredReturnType);
        System.out.println("o erro esta em outro castelo");
    }


    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Function)) return false;
        Function f= (Function) obj;
        if(!f.getName().equals(getName()))return false;
        if(f.getParams().size() != getParams().size()) return false;

        for(int i=0;i<getParams().size();i++){
            if(! f.getParams().get(i).getType().equals(getParams().get(i).getType())) return false;
        }

        return true;
    }
}
