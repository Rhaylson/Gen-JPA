package org.eclipse.acceleo.module.umlToJpa.model;

import java.util.ArrayList;
import java.util.List;

public class Anotacao {
	
	private String nome;
	private List<Propriedade> propriedades = new ArrayList<Propriedade>();
	private Class anotacao;
	
	public void setNome(String nome){
		this.nome = nome;
	}
	public void setPropriedades(List<Propriedade> propriedades){
		this.propriedades = propriedades;
	}
	public void setAnotacao(Class anotacao){
		this.anotacao = anotacao;
	}
	public void addPropriedade(Propriedade propriedade){
		propriedades.add(propriedade);
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public List<Propriedade> getPropriedades(){
		return this.propriedades;
	}
	
	public Class getAnotacao(){
		return this.anotacao;
	}
	
	private String gerarCodigoPropriedades(){
		String retorno = "";
		for (Propriedade p:propriedades){
			retorno = retorno +p+",";
		}
		if(retorno.length()>0){
			retorno = retorno.substring(0, retorno.length()-1);
		}
		return retorno;
	}
	
	public String toString(){
		String retorno = "@"+this.nome;
		if(propriedades.size()>0){
			retorno = retorno +"("+gerarCodigoPropriedades()+")";
		}
		return retorno;
	}
}
