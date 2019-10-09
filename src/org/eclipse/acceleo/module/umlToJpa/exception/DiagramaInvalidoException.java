package org.eclipse.acceleo.module.umlToJpa.exception;

public class DiagramaInvalidoException extends RuntimeException{

	public DiagramaInvalidoException(int erros){
		super("O diagrama apresenta "+erros+" erro(s) verifique o arquivo de Log para mais informações.");
	}
}
