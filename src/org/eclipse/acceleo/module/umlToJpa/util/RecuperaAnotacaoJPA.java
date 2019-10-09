package org.eclipse.acceleo.module.umlToJpa.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.EnumeracaoJPA;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;

public class RecuperaAnotacaoJPA {

	private static Class recuperaClasse(String nomeClasse) {
		try {
			Class c = Class.forName("javax.persistence." + nomeClasse);
			return c;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Classe não encontrada." + e);
		}
	}

	public static Class isOnPersitencePackage(String classe) {
		try {
			Class c = recuperaClasse(classe);
			if (c.getPackage().getName().equals("javax.persistence")) {
				return c;
			}
		} catch (RuntimeException e) {
		}
		return null;
	}

	public static Anotacao recuperarAnotacaoReflection(String nomeAnotacao,
			boolean pegarPropriedades) {
		Anotacao anotacao = null;
		try {
			Class c = recuperaClasse(nomeAnotacao);
			if (c.isAnnotation()) {
				anotacao = new Anotacao();
				anotacao.setNome(c.getSimpleName());
				anotacao.setAnotacao(c);
				if (pegarPropriedades) {
					anotacao.setPropriedades(descobrirPropriedades(c));
				}
			}
		} catch (RuntimeException e) {
			System.out.println(e);
		}
		return anotacao;
	}

	private static List<Propriedade> descobrirPropriedades(Class anotacao) {
		List<Propriedade> propriedades = new ArrayList<>();
		for (Method m : anotacao.getDeclaredMethods()) {
			Propriedade p = new Propriedade();
			p.setNome(m.getName());
			Class c = isOnPersitencePackage(m.getReturnType().getSimpleName());
			if (c != null) {
				p.setReferencia(c);
			}
			p.setTipo(m.getReturnType().getSimpleName());
			propriedades.add(p);
		}
		return propriedades;
	}

	public static EnumeracaoJPA descobrirEnumeracao(String nomeEnumeracao) {
		Class c = recuperaClasse(nomeEnumeracao);
		EnumeracaoJPA eJPA;
		if (c.isEnum()) {
			eJPA = new EnumeracaoJPA();
			eJPA.setClasse(c);
			Map<String, String> valores = new HashMap<>();
			for (Field f : c.getDeclaredFields()) {
				if (!Modifier.isPrivate(f.getModifiers())) {
					valores.put(f.getName(),
							c.getSimpleName() + "." + f.getName());
				}
			}
			eJPA.setValores(valores);
			return eJPA;
		} else {
			throw new RuntimeException("Enumeracao Invalida");
		}
	}
}
