package org.eclipse.acceleo.module.umlToJpa.model;

public class Propriedade {

    private String nome;
    private String valor;
    private String tipo;
    private Class referencia;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNome() {
        return this.nome;
    }

    public String getValor() {
        return this.valor;
    }

    public String toString() {
    	String retorno = "";
    	if(nome!=null){
    		retorno = nome + "=" + valor;
    	}else{
    		retorno = valor;
    	}
        return retorno;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Class getReferencia() {
        return referencia;
    }

    public void setReferencia(Class referencia) {
        this.referencia = referencia;
    }
}
