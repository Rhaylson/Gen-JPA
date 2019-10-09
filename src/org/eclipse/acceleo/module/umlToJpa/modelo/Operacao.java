package org.eclipse.acceleo.module.umlToJpa.modelo;

import java.util.ArrayList;
import java.util.List;

public class Operacao {
	
	private String nome;
	private String visibilidade;
	private Importe retorno;
	private List<Atributo> parametros = new ArrayList<Atributo>();
	private boolean isVoid;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getVisibilidade() {
		return visibilidade;
	}
	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}
	public Importe getRetorno() {
		return retorno;
	}
	public void setRetorno(Importe retorno) {
		this.retorno = retorno;
	}
	public List<Atributo> getParametros() {
		return parametros;
	}
	public void setParametros(List<Atributo> parametros) {
		this.parametros = parametros;
	}
	public void addParametro(Atributo parametro){
		parametros.add(parametro);
	}
	
	public boolean isVoid() {
		return isVoid;
	}
	public void setVoid(boolean isVoid) {
		this.isVoid = isVoid;
	}
	
}
