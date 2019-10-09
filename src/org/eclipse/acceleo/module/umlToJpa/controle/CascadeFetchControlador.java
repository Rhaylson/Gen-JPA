package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.EnumeracaoJPA;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class CascadeFetchControlador {

    private static EnumeracaoJPA fetch;
    private static EnumeracaoJPA cascade;
    private static CascadeFetchControlador controlador;

    public static CascadeFetchControlador getInstance() {
        if (controlador == null) {
            controlador = new CascadeFetchControlador();
        }
        return controlador;
    }

    private CascadeFetchControlador() {
        inicializarEnumeracoes();
    }

    private void inicializarEnumeracoes() {
        fetch = RecuperaAnotacaoJPA.descobrirEnumeracao("FetchType");
        cascade = RecuperaAnotacaoJPA.descobrirEnumeracao("CascadeType");
    }
    
    public Propriedade gerarCascade(String tipo){
    	Propriedade p = new Propriedade();
		p.setNome("cascade");
		p.setReferencia(getCascade().getClasse());
		p.setValor(getCascade().getDeclaracao(tipo));
		return p;
    }
    
    public Propriedade gerarFetch(String tipo){
    	Propriedade p = new Propriedade();
		p.setNome("fetch");
		p.setReferencia(getFetch().getClasse());
		p.setValor(getFetch().getDeclaracao(tipo));
		return p;
    }

	public static EnumeracaoJPA getFetch() {
		return fetch;
	}

	public static void setFetch(EnumeracaoJPA fetch) {
		CascadeFetchControlador.fetch = fetch;
	}

	public static EnumeracaoJPA getCascade() {
		return cascade;
	}

	public static void setCascade(EnumeracaoJPA cascade) {
		CascadeFetchControlador.cascade = cascade;
	}
    
}

