package org.eclipse.acceleo.module.umlToJpa.modelo;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Importe;
import org.eclipse.acceleo.module.umlToJpa.validacao.NormalizadorDeNomes;

public class Atributo {

	private String nome;
	private Importe tipo;
	private String visibilidade;
	private String valorDefault;
	private List<Anotacao> anotacoes = new ArrayList<>();
	private boolean multivalorado;
	private AtributoMultivalorado lista;
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = NormalizadorDeNomes.normalizarNomeAtributo(nome);
	}
	
	public Importe getTipo() {
		return tipo;
	}
	
	public void setTipo(Importe tipo) {
		this.tipo = tipo;
	}
	
	public String getVisibilidade() {
		return visibilidade;
	}

	public void setVisibilidade(String visibilidade) {
		this.visibilidade = visibilidade;
	}

	public List<Anotacao> getAnotacoes() {
		return anotacoes;
	}

	public void setAnotacoes(List<Anotacao> anotacoes) {
		this.anotacoes = anotacoes;
	}
	
	public void addAnotacao(Anotacao anotacao){
		anotacoes.add(anotacao);
	}

	public String getValorDefault() {
		return valorDefault;
	}

	public void setValorDefault(String valorDefault) {
		this.valorDefault = valorDefault;
	}

	public boolean isMultivalorado() {
		return multivalorado;
	}

	public void setMultivalorado(boolean multivalorado) {
		this.multivalorado = multivalorado;
	}

	public AtributoMultivalorado getLista() {
		return lista;
	}

	public void setLista(AtributoMultivalorado lista) {
		this.lista = lista;
	}
}
