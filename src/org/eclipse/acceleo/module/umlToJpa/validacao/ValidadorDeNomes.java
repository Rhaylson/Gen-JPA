package org.eclipse.acceleo.module.umlToJpa.validacao;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.eclipse.acceleo.module.umlToJpa.exception.NomeInvalidoException;

public class ValidadorDeNomes {
	
	private static final Set<String> JAVA_KEY_WORDS = new HashSet<String>(Arrays.asList("abstract", "assert", "boolean",
			"break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else",
			"enum", "extends", "false", "final", "finally", "float", "for", "goto", "if", "implements", "import",
			"instanceof", "int", "interface", "long", "native", "new", "null", "package", "private", "protected",
			"public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw",
			"throws", "transient", "true", "try", "void", "volatile", "while"));
	private Pattern[] firstLetterPattern = new Pattern[3];

	public ValidadorDeNomes() {
		firstLetterPattern[0] = Pattern.compile("[A-Z_$]+");
		firstLetterPattern[1] = Pattern.compile("[a-zA-Z]+");
		firstLetterPattern[2] = Pattern.compile("[a-z$_]+");
	}

	private boolean isKeyword(String nome) {
		return JAVA_KEY_WORDS.contains(nome);
	}

	private boolean validarNome(int tipoPadrao, String texto) {
		Pattern letter = firstLetterPattern[tipoPadrao];
		return letter.matcher(texto.substring(0, 1)).matches();
	}

	private boolean isOnPackage(String pacote, String classe) {
		try {
			Class.forName(pacote + "." + classe);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public boolean isClassifierName(String nome) {
		if (isOnPackage("java.util", nome) || isOnPackage("java.lang", nome)) {
			throw new NomeInvalidoException(nome,
					"a classe informada pertence ao pacote Lang ou Util do Java o que não é permitido.");
		} else if (validarNome(0, nome.substring(0, 1))) {
			return true;
		} else {
			throw new NomeInvalidoException(nome, "classificador com nome incompatível com a linguagem Java.");
		}
	}

	public boolean isJavaOperationName(String nome) {
		if (!isKeyword(nome) && validarNome(1, nome.substring(0, 1))) {
			return true;
		}
		throw new NomeInvalidoException(nome, "método com nome incompatível com a linguagem Java.");
	}

	public boolean isJavaPropertyName(String nome) {
		if (!isKeyword(nome) && validarNome(2, nome)) {
			if(!nome.equals("id")){
			 return true;
			}
		}
		throw new NomeInvalidoException(nome, "atributo com nome incompatível com a linguagem Java ou com o nome id.");
	}
}
