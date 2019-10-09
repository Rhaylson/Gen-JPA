package org.eclipse.acceleo.module.umlToJpa.modelo;

public class Membro {
	
	private Classe classe;
	private String multiplicidade;
	private boolean navegavel;
	private String tipoAgregacao;
	
	public Classe getClasse() {
		return classe;
	}
	public void setClasse(Classe classe) {
		this.classe = classe;
	}
	public String getMultiplicidade() {
		return multiplicidade;
	}
	public void setMultiplicidade(String multiplicidade) {
		this.multiplicidade = multiplicidade;
	}
	public boolean isNavegavel() {
		return navegavel;
	}
	public void setNavegavel(boolean navegavel) {
		this.navegavel = navegavel;
	}
	public String getTipoAgregacao() {
		return tipoAgregacao;
	}
	public void setTipoAgregacao(String tipoAgregacao) {
		this.tipoAgregacao = tipoAgregacao;
	}
}
