package compiler.core;

public class Variable {

    private Type type;
    private String identifier;
    private Expression value;
    private Boolean isConstant;

    public Variable(Type type, String identifier, Boolean isConstant) {
        this.type = type;
        this.identifier = identifier.toLowerCase();
        this.isConstant = isConstant;
    }

    public Type getType() {
        return type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public Expression getValue() {
        return value;
    }

    public void setType(Type type) {
        this.type = type;
    }

//    public void setIdentifier(String identifier) {
//        this.identifier = identifier.toLowerCase();
//    }

    public void setValue(Expression value) {
        this.value = value;
    }

    public void setIsConstant(Boolean isConstant) {
        this.isConstant = isConstant;
    }

    public Boolean getIsConstant() {
        return isConstant;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "identifier='" + identifier + '\'' +
                '}';
    }
}
