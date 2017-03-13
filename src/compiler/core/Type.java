package compiler.core;

public class Type {
    private String typeName;

    public Type(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return this.typeName;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Type))
            return false;
        return this.getName().toLowerCase().equals(((Type) obj).getName().toLowerCase());
    }

    public boolean isAssignmentCompatible(Type t) {
        if (this.typeName.equals("integer") || this.typeName.equals("real")) {
            return (t.getName().equals("real") || t.getName().equals("integer"));
        }
        else if (this.typeName.equals("string")) {
            return (t.getName().equals("string") || t.getName().equals("char"));
        }
        else if (this.typeName.equals("char")) {
            return (t.getName().equals("char"));
        }
        else if (this.typeName.equals("boolean")) {
            return (t.getName().equals("boolean"));
        }

        return false;
    }

    public boolean isNumeric () {
        return (this.typeName.equals("integer") || this.typeName.equals("real"));
    }

    public boolean isRelational () {
        return (this.typeName.equals(""));
    }

    @Override
    public String toString(){
        return getName();
    }

}
