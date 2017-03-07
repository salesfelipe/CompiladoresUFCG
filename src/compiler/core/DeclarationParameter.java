package compiler.core;

import java.lang.Override;
import java.lang.String;

public class DeclarationParameter implements Parameter{
	
	String name;
	Type type;
	
	public DeclarationParameter(Type type, String name) {
		this.name = name;
		this.type = type;
	}

	@Override
	public Type getType() {
		return this.type;
	}
	
	public String getName(){
		return this.name;
	}

	@Override
	public String getIdentifier() {
		return getName();
	}


	@Override
	public String toString() {
		return "DeclarationParameter{" +
				"name=" + name +
				", type=" + type.getName() +
				'}';
	}
}