package org.eclipse.acceleo.module.umlToJpa.mapeamento;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class OneToOneControlador {

	public static Anotacao gerarOneToOneBidirecional(String mapeamento) {
		Propriedade p = new Propriedade();
		p.setNome("mappedBy");
		p.setValor(mapeamento);
		Anotacao anotacao = RecuperaAnotacaoJPA.recuperarAnotacaoReflection(
				"OneToOne", false);
		anotacao.addPropriedade(p);
		return anotacao;
	}

	public static Anotacao gerarOneToOneUnidirecional() {
		return RecuperaAnotacaoJPA.recuperarAnotacaoReflection("OneToOne",
				false);
	}
}
