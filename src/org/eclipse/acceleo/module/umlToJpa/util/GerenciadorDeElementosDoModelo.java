package org.eclipse.acceleo.module.umlToJpa.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.module.umlToJpa.controle.AssociacaoControlador;
import org.eclipse.acceleo.module.umlToJpa.controle.ClasseControlador;
import org.eclipse.acceleo.module.umlToJpa.exception.DiagramaInvalidoException;
import org.eclipse.acceleo.module.umlToJpa.modelo.Associacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Classe;

public class GerenciadorDeElementosDoModelo {

	private static ClasseControlador cc;
	private static Map<String, Classe> classes;
	private static Map<String, String> herancas;
	private static Map<Associacao, List<Associacao>> associacoes;
	private static String tipoLista;
	private static String tipoHeranca;
	private static String destino;

	public GerenciadorDeElementosDoModelo(String tipoLista, String tipoHeranca,String pastaDestino) {
		CarregaArquivoXml.carregaClasses();
		classes = CarregaArquivoXml.getClasses();
		herancas = CarregaArquivoXml.getHerancas();
		associacoes = CarregaArquivoXml.gerarAssociacoes();
		cc = new ClasseControlador();
		GerenciadorDeElementosDoModelo.tipoLista = tipoLista;
		GerenciadorDeElementosDoModelo.tipoHeranca = tipoHeranca;
		GerenciadorDeElementosDoModelo.destino = pastaDestino;
	}

	public int validarDiagrama() {
		List<Erro> erros = new ArrayList<Erro>();
		for(String s:CarregaArquivoXml.getClassesHerancaMultipla()){
			Erro e = new Erro();
			e.setElemento(s);
			e.setMensagem("Essa classe possui Herança Múltipla não permitida em Java.");
			e.setTipo("Herança Múltipla");
			erros.add(e);
		}
		for (String s : classes.keySet()) {
			Classe c = classes.get(s);
			String superClasse = herancas.get(c.getId());// Vai trazer o nome da
															// superClasse dessa
															// classe.
			if (superClasse != null) {
				c.setSuperclasse(classes.get(superClasse));
			} else {
				verificaSubclasse(c);
			}
			erros.addAll(cc.verificarErrosClasse(c));
		}
		erros.addAll(processarAssociacoes());
		if (erros.size() == 0) {
			for (String s : classes.keySet()) {
				Classe c = classes.get(s);
				cc.addAnotacaoEntityId(c, tipoHeranca);
			}
		} else {
			LogDeErros log = new LogDeErros();
			for (Erro e : erros) {
				log.addErro(e);
			}
			log.gravarConteudoNoArquivo();
		}
		return erros.size();
	}

	public void gerarCodigo() {
		GerenciadorDeArquivosJava gerenciadorJava = new GerenciadorDeArquivosJava();
		gerenciadorJava.setDiretorio(new File(destino));
		int erros = validarDiagrama();
		if (erros == 0) {//Sem erros
			for (String s : classes.keySet()) {
				Classe c = classes.get(s);
				gerenciadorJava.criarArquivo(c.getNome(), cc.gerarCodigo(c));
			}
		}else{
			throw new DiagramaInvalidoException(erros);
		}
	}

	public static Map<String, Classe> getClasses() {
		return classes;
	}

	public static Map<Associacao, List<Associacao>> getAssociacoes() {
		return associacoes;
	}

	public void verificaSubclasse(Classe c) {
		for (String s : herancas.keySet()) {
			if (herancas.get(s).equals(c.getId())) {
				c.setPossuiSubClasses(true);
				break;
			}
		}
	}

	public List<Erro> processarAssociacoes() {
		List<Erro> erros = new ArrayList<Erro>();
		AssociacaoControlador assControlador = new AssociacaoControlador();
		for (Associacao a1 : associacoes.keySet()) {
			erros.addAll(assControlador.processarAssociacoes(a1,
					associacoes.get(a1), tipoLista));
		}
		return erros;
	}
}
