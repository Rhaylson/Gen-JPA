package org.eclipse.acceleo.module.umlToJpa.exception;

import org.eclipse.acceleo.module.umlToJpa.modelo.Associacao;

public class AssociacaoInvalidaException extends RuntimeException{
	public AssociacaoInvalidaException(Associacao associacao){
		super("A associacao: "+associacao.getNome()+" entre "+associacao.getMembroA().getClasse().getNome()+" e "+associacao.getMembroB().getClasse().getNome()+ " apresenta erros.");
	}
}
