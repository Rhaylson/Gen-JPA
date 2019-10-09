package org.eclipse.acceleo.module.umlToJpa.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.acceleo.module.umlToJpa.controle.AssociacaoControlador;
import org.eclipse.acceleo.module.umlToJpa.modelo.Associacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.modelo.Classe;
import org.eclipse.acceleo.module.umlToJpa.modelo.Membro;
import org.eclipse.acceleo.module.umlToJpa.modelo.Operacao;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

public class CarregaArquivoXml {
	private static Document documento;
	private static GerenciadorDeTipos tipos;
	private static Map<String, Classe> classes;
	private static Map<String, String> herancas;
	private static List<Associacao> associacoes;
	private static Set<String> classesHerancaMultipla;

	public static void carregaClasses() {
		AssociacaoControlador ac = new AssociacaoControlador();
		tipos = GerenciadorDeTipos.getInstance();
		classes = new HashMap<String, Classe>();
		herancas = new HashMap<String, String>();
		associacoes = new ArrayList<Associacao>();
		classesHerancaMultipla = new HashSet<String>();

		SAXBuilder builder = new SAXBuilder();
		try {
			documento = builder
					.build("C:/Users/Rhaylson/OneDrive/workspace/org.eclipse.acceleo.module.umlToJpa/generate/model.xml");
			Element raiz = documento.getRootElement();
			for (Element e : raiz.getChildren("classe")) {
				Classe c = new Classe(e.getChildText("id"));
				c.setNome(e.getChildText("nome"));
				int qtdHerancas = e.getChildren("heranca").size();
				if (qtdHerancas <= 1) {
					for (Element her : e.getChildren("heranca")) {
						herancas.put(her.getChildText("specific"),
								her.getChildText("general"));
					}
				} else {
					classesHerancaMultipla.add(c.getId());
				}
				for (Element att : e.getChildren("atributo")) {
					Atributo atributo = new Atributo();
					atributo.setVisibilidade(att.getChildText("visibilidade"));
					atributo.setTipo(tipos.recuperaTipo(att
							.getChildText("tipo")));// Se não encontrar o tipo
													// retorna null.
					atributo.setNome(att.getChildText("nome"));
					c.addAtributo(atributo);
				}
				for (Element met : e.getChildren("metodo")) {
					Operacao metodo = new Operacao();
					metodo.setVisibilidade("visibilidade");
					metodo.setNome(met.getChildText("nome"));
					if (met.getChildText("retorno").equals("void")) {
						metodo.setVoid(true);
					} else {
						metodo.setRetorno(tipos.recuperaTipo(met
								.getChildText("retorno")));
					}
					for (Element param : met.getChildren("parametro")) {
						Atributo a = new Atributo();
						a.setNome(param.getChildText("nome"));
						a.setTipo(tipos.recuperaTipo(param.getChildText("tipo")));
						metodo.addParametro(a);
					}
					c.addMetodo(metodo);
				}
				classes.put(c.getId(), c);
			}
			tipos.setTiposDiagrama(classes);
			for (Element e : raiz.getChildren("associacao")) {
				Associacao ass = new Associacao();
				Membro membroA = new Membro();
				Membro membroB = new Membro();

				Element ma = e.getChild("membroA");
				Element mb = e.getChild("membroB");

				ass.setNome(e.getChildText("nome"));

				membroA.setClasse(classes.get(ma.getChildText("classe")));
				membroA.setMultiplicidade(ass.normalizaMultiplicidade(
						ma.getChildText("minimo"), ma.getChildText("maximo")));
				membroA.setNavegavel(Boolean.parseBoolean(ma
						.getChildText("navegavel")));
				membroA.setTipoAgregacao(ma.getChildText("agregacao"));

				membroB.setClasse(classes.get(mb.getChildText("classe")));
				membroB.setMultiplicidade(ass.normalizaMultiplicidade(
						mb.getChildText("minimo"), mb.getChildText("maximo")));
				membroB.setNavegavel(Boolean.parseBoolean(mb
						.getChildText("navegavel")));
				membroB.setTipoAgregacao(mb.getChildText("agregacao"));

				ass.setIdMembroA(ma.getChildText("classe"));
				ass.setIdMembroB(mb.getChildText("classe"));
				ass.setMembroA(membroA);
				ass.setMembroB(membroB);
				ass.setMultiplicidade(ac.tipoMultiplicidade(ass));

				associacoes.add(ass);
			}
		} catch (JDOMException | IOException e) {
			throw new RuntimeException("Erro na manipulação do XML."+e);
		}
	}

	public static Map<Associacao, List<Associacao>> gerarAssociacoes() {
		Associacao a1 = null;
		Associacao a2 = null;
		Map<Associacao, List<Associacao>> relacoes = new HashMap<Associacao, List<Associacao>>();
		for (int i = 0; i < associacoes.size(); i++) {

			a1 = associacoes.get(i);
			List<Associacao> relacionadas = new ArrayList<>();
			for (int j = 0; j < associacoes.size(); j++) {
				a2 = associacoes.get(j);
				if (i != j) {
					if (a1.equals(a2)) {
						relacionadas.add(a2);
					}
				}
			}
			relacoes.put(a1, relacionadas);
		}
		return relacoes;
	}

	public static Map<String, Classe> getClasses() {
		return classes;
	}

	public static Map<String, String> getHerancas() {
		return herancas;
	}

	public static List<Associacao> getAssociacoes() {
		return associacoes;
	}
	
	public static Set<String> getClassesHerancaMultipla(){
		return classesHerancaMultipla;
	}
}
