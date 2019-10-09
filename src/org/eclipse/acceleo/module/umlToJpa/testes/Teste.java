package org.eclipse.acceleo.module.umlToJpa.testes;

import java.util.Map;

import org.eclipse.acceleo.module.umlToJpa.controle.AtributoControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.EntityControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.IdControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.InheritanceControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.TemporalControlador;
import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.EnumeracaoJPA;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class Teste {

	public static void main(String[] args) {
		/*	EnumeracaoJPA enumeration = RecuperaAnotacaoJPA.descobrirEnumeracao("TemporalType");
	Map<String,String> m = 	enumeration.getValores();
	for(String s:m.keySet()){
			System.out.println(s +" - "+m.get(s));
		}
	TemporalControlador tc = TemporalControlador.getInstance();
	System.out.println(tc.gerarTemporal("TIME"));*/
	InheritanceControlador ic = InheritanceControlador.getInstance();
	EnumeracaoJPA enumeration = ic.getInher();
	Map<String,String> m = 	enumeration.getValores();
	for(String s:m.keySet()){
			System.out.println(s +" - "+m.get(s));
		}
	}
}
