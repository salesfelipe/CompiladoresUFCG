package compiler.core;

public class CallParameter implements Parameter{

	Type type;
	Object value;
	
	public CallParameter(Type type, Object value) {
		this.value = value;
		this.type = type;
	}
	
	@Override
	public Type getType() {
		return this.type;
	}
	
	public Object getValue(){
		return this.value;
	}

	@Override
	public String getIdentifier() {
		return null;
	}

}