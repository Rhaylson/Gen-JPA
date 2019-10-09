package org.eclipse.acceleo.module.umlToJpa.exception;

public class TipoInvalidoException extends RuntimeException{

	public TipoInvalidoException(String nomeAtributo,String tipo){
		super("O tipo "+tipo+" do atributo "+nomeAtributo+" não foi reconhecido.");
	}
}
