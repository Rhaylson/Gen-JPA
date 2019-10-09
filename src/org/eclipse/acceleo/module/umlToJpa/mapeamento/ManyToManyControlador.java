package org.eclipse.acceleo.module.umlToJpa.mapeamento;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class ManyToManyControlador {
	
	public static Anotacao gerarManyToMany(){
		return RecuperaAnotacaoJPA.recuperarAnotacaoReflection("ManyToMany", false);
	}
	
	public static Anotacao gerarManyToManyBidirecional(String mapeamento) {
		Propriedade p = new Propriedade();
		p.setNome("mappedBy");
		p.setValor(mapeamento);
		Anotacao anotacao = RecuperaAnotacaoJPA.recuperarAnotacaoReflection(
				"ManyToMany", false);
		anotacao.addPropriedade(p);
		return anotacao;
	}
}
