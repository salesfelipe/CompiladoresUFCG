package compiler.core;

import java.util.ArrayList;
import java.util.List;

public class Procedure extends ScopedEntity{
    private List<Parameter> params;

    public Procedure(String name, ArrayList<Parameter> params){
        super(name);
        if(params != null){
            this.params = params;
        }else{
            this.params = new ArrayList<Parameter>();
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
