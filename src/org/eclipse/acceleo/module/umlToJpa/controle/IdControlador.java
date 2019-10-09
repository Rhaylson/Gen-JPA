package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.util.GerenciadorDeTipos;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class IdControlador {

	private static Anotacao base = RecuperaAnotacaoJPA
			.recuperarAnotacaoReflection("Id", false);
	private static GerenciadorDeTipos gt = GerenciadorDeTipos.getInstance();

	public static Atributo gerarId() {
		Atributo a = new Atributo();
		a.setNome("id");
		a.setTipo(gt.recuperaTipo("long"));
		a.setVisibilidade("private");
		a.addAnotacao(base);
		a.addAnotacao(RecuperaAnotacaoJPA.recuperarAnotacaoReflection(
				"GeneratedValue", false));
		return a;
	}
}
