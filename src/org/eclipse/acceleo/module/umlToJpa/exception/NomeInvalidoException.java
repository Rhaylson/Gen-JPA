package org.eclipse.acceleo.module.umlToJpa.exception;

public class NomeInvalidoException extends RuntimeException{

	public NomeInvalidoException(String elemento,String mensagem) {
		super(elemento+" : "+mensagem);
	}
}
