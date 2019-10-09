package org.eclipse.acceleo.module.umlToJpa.modelo;

public class Importe {

	private String nomeClasse;
	private String pacote;
	private String valorDefault;
	
	public void setNomeClasse(String nomeClasse){
		this.nomeClasse = nomeClasse;
	}
	
	public void setPacote(String pacote){
		this.pacote = pacote;
	}
	
	public String getNomeClasse(){
		return this.nomeClasse;
	}
	
	public String getPacote(){
		return this.pacote;
	}
	
	public String toString(){
		return pacote+"."+nomeClasse;
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}
}
