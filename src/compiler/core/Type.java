package compiler.core;

import java.util.ArrayList;
import java.util.List;

public class Type {
    private String typeName;

    public List<Type> compatibleTypes;

    public Type(String typeName) {
        this.typeName = typeName.toLowerCase();
        this.compatibleTypes = new ArrayList<Type>();
    }

    public String getName() {
        return this.typeName;
    }

    public void addCompatibility(Type type) {
        compatibleTypes.add(type);
    }

    public boolean isCompatible(Type type) {
        return compatibleTypes.contains(type) || this.typeName.equals(type.getName());
    }

    public boolean isNumeric () {
        return (this.typeName.equals("integer") || this.typeName.equals("real"));
    }

    public static List<Type> getPrimitiveTypes() {

        Type integer = new Type("Integer");
        Type real = new Type("Real");
        Type bool = new Type("Boolean");
        Type string = new Type("String");
        Type character = new Type("Char");
        Type nil = new Type("Nil");

        integer.addCompatibility(real);
        real.addCompatibility(integer);
        string.addCompatibility(character);

        List<Type> result = new ArrayList<Type>() {
            {
                add(integer);
                add(real);
                add(bool);
                add(string);
                add(character);
                add(nil);
            }
        };

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Type))
            return false;
        return this.getName().equals(((Type) obj).getName());
    }

    @Override
    public String toString(){
        return getName();
    }

}
