package compiler.core;

public class Procedure extends ScopedEntity{
    private List<Parameter> params;
    private name

    public Procedure(String name, ArrayList<Parameter> params){
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
        if(f.getParams().size() != getParams().size()) return false;

        for(int i=0;i<getParams().size();i++){
            if(! f.getParams().get(i).getType().equals(getParams().get(i).getType())) return false;
        }

        return true;
    }
}
