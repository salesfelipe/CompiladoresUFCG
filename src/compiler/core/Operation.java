package compiler.core;

public class Operation {
    String operatorName;

    public Operation(String operator) {
        this.operatorName = operator;
    }

    public boolean isOperationCompatible(Type t1, Type t2) {
        String op = this.operatorName;
        String numericOperators = "+ - * / %";
        String relationalOperators = "= >= > <= < <>";
        String booleanOperators[] = {"and", "and then", "or", "or else"};
        if (numericOperators.contains(op)) {
            return t1.isNumeric() && t2.isNumeric();
        }
        else if (relationalOperators.contains(op)) {
            return t1.getName().equals(t2.getName());
        }



        return false;
    }

}
