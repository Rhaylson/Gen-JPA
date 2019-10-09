package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.util.RecuperaAnotacaoJPA;

public class EntityControlador {

	private static Anotacao base = RecuperaAnotacaoJPA.recuperarAnotacaoReflection("Entity",false);
	
	public static String gerarEntidade(){
		return base.toString();
	}
	
	public static  Anotacao getBase(){
		return base;
	}
}
