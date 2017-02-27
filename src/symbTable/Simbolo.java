package symbTable;

import type.Type;

public class Simbolo {

	Type tipo;
	String nome;
	boolean constante;

	public Simbolo(String nome, Type tipo) {
		this(nome, tipo, false);
	}
	
	public Simbolo(String nome, Type tipo, boolean constante) {
		this.nome = nome;
		this.tipo = tipo;
		this.constante = constante;
	}

	public Type getTipo() {
		return tipo;
	}
	
	public String getNome() {
		return nome;
	}
	
	public boolean ehConstante() {
		return constante;
	}
	
}
