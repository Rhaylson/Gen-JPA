package org.eclipse.acceleo.module.umlToJpa.model;

import java.util.List;

public class CollectionsImport extends Importe{

	private List<Importe> implementacoes;
	
	public CollectionsImport(String classe, String pacote, List<Importe> implementacoes){
		super.setNomeClasse(classe);
		super.setPacote(pacote);
		this.implementacoes = implementacoes;
	}
	
	public List<Importe> getImplementacoes(){
		return this.implementacoes;
	}
}
