package org.eclipse.acceleo.module.umlToJpa.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.acceleo.module.umlToJpa.modelo.Classe;
import org.eclipse.acceleo.module.umlToJpa.modelo.Importe;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class GerenciadorDeTipos {

	private Document documento;
	private static Map<String, Classe> tiposDiagrama;
	private static Map<String, Importe> tiposBasicos;
	private static Map<String, List<Importe>> colecoes;
	private static GerenciadorDeTipos carregaArquivo;
	private static Set<Importe> importes;

	private GerenciadorDeTipos() {
		importes = new HashSet<>();
		tiposBasicos = carregarTiposBasicos();
		colecoes = carregarColecoes();
		tiposDiagrama = GerenciadorDeElementosDoModelo.getClasses();
		carregarValorDefault();
	}

	public static synchronized GerenciadorDeTipos getInstance() {
		if (carregaArquivo == null) {
			carregaArquivo = new GerenciadorDeTipos();
		}
		return carregaArquivo;
	}

	private Importe retornaTipoBasico(String nome) {
		Importe importe = null;
		importe = tiposBasicos.get(nome);
		if (importe == null) {
			for (String s : tiposBasicos.keySet()) {
				if (tiposBasicos.get(s).getNomeClasse().equals(nome)) {
					importe = tiposBasicos.get(s);
					break;
				}
			}
		}
		return importe;
	}

	public Importe recuperaTipo(String nome) {
		Importe importe = retornaTipoBasico(nome);
		if (importe == null) {
			if (tiposDiagrama.get(nome) == null) {
				for (String s : colecoes.keySet()) {
					if (nome.equals(s)) {
						importe = new Importe();
						importe.setNomeClasse(s);
						importe.setPacote(colecoes.get(s).get(0).getPacote());
					} else {
						for (Importe implementacao : colecoes.get(s)) {
							if (implementacao.getNomeClasse().equals(nome)) {
								importe = implementacao;
								break;
							}
						}
					}
				}
			}else{
				importe = new Importe();
				importe.setNomeClasse(tiposDiagrama.get(nome).getNome());
				importe.setPacote("root");
			}
		}
		return importe;
	}

	private Map<String, Importe> carregarTiposBasicos() {
		Map<String, Importe> tipos = new HashMap<>();
		SAXBuilder builder = new SAXBuilder();
		try {
			documento = builder.build(
					"C:\\Users\\Rhaylson\\OneDrive\\workspace\\org.eclipse.acceleo.module.umlToJpa\\xmls\\primitivos.xml");
			Element raiz = documento.getRootElement();
			for (Element elemento : raiz.getChildren("tipo")) {
				Importe i = new Importe();
				i.setNomeClasse(elemento.getChildText("classe"));
				i.setPacote(elemento.getChildText("pacote"));
				tipos.put(elemento.getChildText("nome"), i);
				importes.add(i);
			}
		} catch (JDOMException | IOException e) {
			throw new RuntimeException("Erro na manipulação do XML.");
		}
		return tipos;
	}

	private void carregarValorDefault() {
		SAXBuilder builder = new SAXBuilder();
		try {
			documento = builder.build(
					"C:\\Users\\Rhaylson\\OneDrive\\workspace\\org.eclipse.acceleo.module.umlToJpa\\xmls\\default.xml");
			Element raiz = documento.getRootElement();
			for (Element elemento : raiz.getChildren("default")) {
				addValorDefault(elemento.getChildText("tipo"), elemento.getChildText("valor"));
			}
		} catch (JDOMException | IOException e) {
			throw new RuntimeException("Erro na manipulação do XML." + e);
		}
	}

	private Map<String, List<Importe>> carregarColecoes() {
		SAXBuilder builder = new SAXBuilder();
		Map<String, List<Importe>> colecoes = new HashMap<>();
		try {
			documento = builder.build(
					"C:\\Users\\Rhaylson\\OneDrive\\workspace\\org.eclipse.acceleo.module.umlToJpa\\xmls\\implementacoes.xml");
			Element raiz = documento.getRootElement();
			for (Element elemento : raiz.getChildren("implementacao")) {
				List<Importe> imp = new ArrayList<>();
				for (Element filho : elemento.getChildren("concreta")) {
					Importe i = new Importe();
					i.setNomeClasse(filho.getText());
					i.setPacote(elemento.getChildText("pacote"));
					imp.add(i);
				}
				colecoes.put(elemento.getChildText("classe"), imp);
			}
		} catch (JDOMException | IOException e) {
			throw new RuntimeException("Erro na manipulação do XML." + e);
		}
		return colecoes;
	}

	private void addValorDefault(String classe, String valor) {
		for (Importe i : importes) {
			if (i.getNomeClasse().equals(classe)) {
				i.setValorDefault(valor);
			}
		}
	}

	public static void setTiposDiagrama(Map<String, Classe> tiposDiagrama) {
		GerenciadorDeTipos.tiposDiagrama = tiposDiagrama;
	}
	
}
