package compiler.core;

import compiler.exceptions.InvalidFunctionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Function extends ScopedEntity{

    private Type declaredReturnType;
    private Type returnType;
    private List<Parameter> params;

    public Function(String name, ArrayList<Parameter> params){
        super(name);
        if(params != null){
            this.params = params;
        }else{
            this.params = new ArrayList<Parameter>();
        }
        
        initialize();
    }
    
    private void initialize() {
    	for (int i = 0; i < params.size(); i++) {
			addIdentifier(params.get(i).getIdentifier());
			addVariable(new Variable(params.get(i).getType() ,params.get(i).getIdentifier(), false));
		}
    }
    
    public boolean isReturnValid(){
        return this.declaredReturnType.equals(this.returnType);
    }

    public Type getDeclaredReturnType() {
        return declaredReturnType;
    }

    public Type getReturnType(){
        return returnType;
    }

    public List<Parameter> getParams() {
        return this.params;
    }

    public void setDeclaredReturnedType(Type type) {
        this.declaredReturnType = type;
    }

    public void validateReturnedType() throws InvalidFunctionException { // Checks if the function returned what it was supposed to..
        if (!returnType.equals(declaredReturnType))
            throw new InvalidFunctionException("Function " + getName() + " was supposed to return " + declaredReturnType);
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
