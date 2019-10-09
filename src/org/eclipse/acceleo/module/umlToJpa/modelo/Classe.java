package org.eclipse.acceleo.module.umlToJpa.modelo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;

public class Classe extends Classificador{
	
	
	private List<Anotacao> anotacoes;
	private Classe superclasse;
	private boolean possuiSubClasses;
	
	public Classe(String id){
		super.setId(id);
		anotacoes = new ArrayList<Anotacao>();
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}
	
	public void addAnotacao(Anotacao a){
		anotacoes.add(a);
	}

	public Classe getSuperclasse() {
		return superclasse;
	}

	public void setSuperclasse(Classe superclasse) {
		this.superclasse = superclasse;
	}

	public boolean isPossuiSubClasses() {
		return possuiSubClasses;
	}

	public void setPossuiSubClasses(boolean possuiSubClasses) {
		this.possuiSubClasses = possuiSubClasses;
	}
	
}
