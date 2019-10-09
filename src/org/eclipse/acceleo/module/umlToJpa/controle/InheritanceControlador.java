package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.EnumeracaoJPA;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;
import org.eclipse.gmf.runtime.common.ui.services.icon.GetIconOperation;

public class InheritanceControlador {

	    private static EnumeracaoJPA inheritanceType;
	    private static InheritanceControlador controlador;
	    
	    private InheritanceControlador() {
	        inicializarEnumeracoes();
	    }
	    
	    public static InheritanceControlador getInstance() {
	        if (controlador == null) {
	            controlador = new InheritanceControlador();
	        }
	        return controlador;
	    }

	    private void inicializarEnumeracoes() {
	    	inheritanceType = RecuperaAnotacaoJPA.descobrirEnumeracao("InheritanceType");
	    }
	    
	    public Anotacao getInheritance(String tipo){
	    	Propriedade p = new Propriedade();
	    	p.setNome("strategy");
	    	p.setReferencia(inheritanceType.getClasse());
	    	p.setValor(inheritanceType.getDeclaracao(tipo));
	    	Anotacao anotacao = new Anotacao();
	    	anotacao = RecuperaAnotacaoJPA.recuperarAnotacaoReflection("Inheritance", false);
	    	anotacao.addPropriedade(p);
	    	return anotacao;
	    }
	    public EnumeracaoJPA getInher(){
	    	return inheritanceType;
	    }
}
