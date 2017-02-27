package type;

import java.util.*;

public class TypeFunction extends Type {
	
	Type TypeRetorno;
	List<Type> TypeParametros;

	public TypeFunction(Type TypeRetorno) {
		this(TypeRetorno, new ArrayList<Type>());
	}
	
	public TypeFunction(Type TypeRetorno, List<Type> parametros) {
		super(Type.FUNCAO, TypeRetorno.width, TypeRetorno.val);
		this.TypeRetorno = TypeRetorno;
		TypeParametros = parametros;
	}
	
	public void addParamType(List<Type> Type) {
		TypeParametros.addAll(Type);
	}
	
	public Type getTypeRetorno() {
		return TypeRetorno;
	}
	
	public void setTypeRetorno(Type Type) {
		TypeRetorno = Type;
	}
	
	public List<Type> getTypeParametros() {
		return TypeParametros;
	}
	
	public String getNome() {
		return val;
	}

}
