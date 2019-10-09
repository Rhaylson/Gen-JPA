package org.eclipse.acceleo.module.umlToJpa.modelo;

import java.util.ArrayList;
import java.util.List;


public abstract class Classificador {

	private String id;
	private String nome;
	private List<Atributo> atributos = new ArrayList<Atributo>();
	private List<Operacao> metodos = new ArrayList<Operacao>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Atributo> getAtributos() {
		return atributos;
	}

	public void setAtributos(List<Atributo> atributos) {
		this.atributos = atributos;
	}

	public void addAtributo(Atributo atributo) {
		atributos.add(atributo);
	}
	
	public List<Operacao> getMetodos() {
		return metodos;
	}

	public void setMetodos(List<Operacao> metodos) {
		this.metodos = metodos;
	}

	public void addMetodo(Operacao metodo){
		metodos.add(metodo);
	}
}
