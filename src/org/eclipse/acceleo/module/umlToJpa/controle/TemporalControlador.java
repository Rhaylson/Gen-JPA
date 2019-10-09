package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.EnumeracaoJPA;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class TemporalControlador {

	private static EnumeracaoJPA temporalType;
	private static TemporalControlador controlador;

	private TemporalControlador() {
		inicializarEnumeracoes();
	}

	public static TemporalControlador getInstance() {
		if (controlador == null) {
			controlador = new TemporalControlador();
		}
		return controlador;
	}

	private void inicializarEnumeracoes() {
		temporalType = RecuperaAnotacaoJPA.descobrirEnumeracao("TemporalType");
	}
	
	public Anotacao getTemporal(String tipo){
		Anotacao anotacao = RecuperaAnotacaoJPA.recuperarAnotacaoReflection("Temporal", false);
		if(tipo!=null){
			Propriedade p = new Propriedade();
			p.setValor(temporalType.getDeclaracao(tipo));
			p.setReferencia(temporalType.getClasse());
			anotacao.addPropriedade(p);
		}
    	return anotacao;
    }
}
