package compiler.core;

import java.util.HashMap;

public class ScopedEntity extends  NamedEntity {
    private HashMap<String, Variable> variables;
    private HashMap<String, Type> types;


    public ScopedEntity(String name) {
        super(name);
    }
}
