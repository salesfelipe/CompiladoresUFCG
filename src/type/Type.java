package type;

import java.util.HashMap;

public class Type {
	
	public static final int CHAR = 1;
	public static final int INTEGER = 2;
	public static final int REAL = 3;
	public static final int BOOLEAN = 4;
	public static final int TIPO_VOID = 5;
	public static final int ERRO = 6;
 	public static final int NOME = 7;
	public static final int ARRAY = 8;
	public static final int STRING = 9;
	public static final int BYTE = 10;
	public static final int SHORTINT = 11;
	public static final int DOUBLE = 12;
	public static final int LONGINT = 13;
	public static final int WORD = 14;
	public static final int REFERENCIA = 90;
	public static final int PRODUCT = 91;
	public static final int FUNCAO = 93;
	public static final int NIL = 0;
	
	static HashMap<String, Type> tipos = new HashMap<String, Type>();

	int tag;
	int width = 0;
	String val;
	
	public Type(int tag, int width, String val) {
		this.tag = tag;
		this.width = width;
		this.val = val;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getTag() {
		return tag;
	}
	
	public String getVal() {
		return val;
	}
	
	public static void criaTipos() {
		tipos.put(INTEGER+"", new Type(INTEGER, 2, "integer"));
		tipos.put(LONGINT+"", new Type(LONGINT, 4, "longint"));
		tipos.put(SHORTINT+"", new Type(SHORTINT, 1, "integer"));
		tipos.put(REAL+"", new Type(REAL, 6, "real"));
		tipos.put(DOUBLE+"", new Type(DOUBLE, 8, "double"));
		tipos.put(WORD+"", new Type(WORD, 2, "word"));
		tipos.put(BYTE+"", new Type(BYTE, 1, "byte"));
		tipos.put(CHAR+"", new Type(CHAR, 1, "char"));
		tipos.put(STRING+"", new Type(STRING, 8, "string"));
		tipos.put(BOOLEAN+"", new Type(BOOLEAN, 1, "boolean"));
		tipos.put(TIPO_VOID+"", new Type(TIPO_VOID, 0, "void"));
		tipos.put(NIL+"", new Type(NIL, 0, "nil"));
		tipos.put(ERRO+"", new Type(ERRO, 0, "error"));
	}
	
	public static Type getTipo(int tag) {
		return tipos.get(""+tag);
	}
	
	public static Type getTipo(String val) {
		for(Type tipo : tipos.values()) {
			if(tipo.getVal().equals(val))
				return tipo;
		}
		return null;
	}
	
	public static Type maximo(Type t1, Type t2) {
		return t1.width > t2.width ? t1 : t2;
	}
}
