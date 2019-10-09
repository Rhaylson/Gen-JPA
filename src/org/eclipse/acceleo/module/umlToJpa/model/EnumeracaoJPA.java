package org.eclipse.acceleo.module.umlToJpa.model;

import java.util.Map;

public class EnumeracaoJPA {

	private Class classe;
	private Map<String, String> valores;

	public Class getClasse() {
		return classe;
	}

	public void setClasse(Class classe) {
		this.classe = classe;
	}

	public Map<String, String> getValores() {
		return valores;
	}

	public void setValores(Map<String, String> valores) {
		this.valores = valores;
	}

	public String getImport() {
		return classe.getName();
	}

	public String getDeclaracao(String prop) {
		return valores.get(prop);
	}
}
