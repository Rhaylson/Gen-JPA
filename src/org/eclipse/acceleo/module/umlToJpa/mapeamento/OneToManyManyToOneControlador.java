package org.eclipse.acceleo.module.umlToJpa.mapeamento;

import org.eclipse.acceleo.module.umlToJpa.controle.CascadeFetchControlador;
import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class OneToManyManyToOneControlador {

	private CascadeFetchControlador cfc = CascadeFetchControlador.getInstance();
	
	public static Anotacao gerarOneToManyUnidirecional(){
		return RecuperaAnotacaoJPA.recuperarAnotacaoReflection("OneToMany", false);
	}
	public static Anotacao gerarManyToOneUnidirecional(){
		return RecuperaAnotacaoJPA.recuperarAnotacaoReflection("ManyToOne", false);
	}
	public static Anotacao oneToManyBidirecional(String mapeamento){
		Propriedade p = new Propriedade();
		p.setNome("mappedBy");
		p.setValor(mapeamento);
		Anotacao anotacao = gerarOneToManyUnidirecional();
		anotacao.addPropriedade(p);
		return anotacao;
	}
}
