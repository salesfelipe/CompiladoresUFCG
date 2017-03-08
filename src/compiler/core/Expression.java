package compiler.core;

public class Expression {
    private Type type;
    private String value;

    public Expression(Type t) {
        this.type = t;
    }

    public Expression(String name) {
        type = new Type("UNKNOWN");
    }

    public Expression(Type t, String value) {
        this.type = t;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public boolean isNumeric() {
        return  getType().getName().equals("int")
                ||getType().getName().equals("float")
                ||getType().getName().equals("long")
                ||getType().getName().equals("double");
    }

    public String toString(){
        return "Expression of type: " + getType();
    }

}
