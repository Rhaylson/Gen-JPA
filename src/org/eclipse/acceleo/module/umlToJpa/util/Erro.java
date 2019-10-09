package org.eclipse.acceleo.module.umlToJpa.util;

public class Erro {
	
	private String elemento;
	private String tipo;
	private String mensagem;
	
	public Erro(){
		
	}
	
	public Erro(String elemento,String tipo,String mensagem){
		this.elemento = elemento;
		this.tipo = tipo;
		this.mensagem  = mensagem;
	}
	
	public String getElemento() {
		return elemento;
	}
	
	public void setElemento(String elemento) {
		this.elemento = elemento;
	}
	
	public String getTipo() {
		return tipo;
	}
	
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
