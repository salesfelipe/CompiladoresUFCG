package compiler.core;

import compiler.exceptions.InvalidFunctionException;
import compiler.exceptions.InvalidNameException;

import java.util.*;

public class Function extends ScopedEntity{

    private Type declaredReturnType;
    private boolean hasReturn;

    public Function(String name, ArrayList<Parameter> params) throws InvalidNameException {
        super(name);

        hasReturn = false;

        if(params != null){
            setParams(params);
        }else{
            setParams(new ArrayList<Parameter>());
        }
        initialize();
        setIsProcedure(false);
    }
    
    public Type getDeclaredReturnType() {
        return declaredReturnType;
    }

    public void setDeclaredReturnedType(Type type) {
        this.declaredReturnType = type;
    }

    public void validateReturnedType() throws InvalidFunctionException { // Checks if the function returned what it was supposed to..

        if(!hasReturn) {
            throw new InvalidFunctionException("A Função " + getName() + " não tem um retorno");
        }
    }

    public void setHasReturn(boolean b) {
        hasReturn = b;
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
