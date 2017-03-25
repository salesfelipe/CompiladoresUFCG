package compiler.core;

public class Expression {
    private Type type;
    private String value;

    public Expression(Type t) {
        this.type = t;
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
        return  getType().getName().equals("integer")
                ||getType().getName().equals("real");
    }

    public String toString(){
        return "Expression of type: " + getType();
    }

}
