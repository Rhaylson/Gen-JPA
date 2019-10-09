package org.eclipse.acceleo.module.umlToJpa.validacao;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class NormalizadorDeNomes {

	private static final String remover = "[^A-Za-z0-9_]";
	
	private static String removerAcentos(String texto){
		String temp = Normalizer.normalize(texto, Normalizer.Form.NFD);
		Pattern padrao = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		texto = padrao.matcher(temp).replaceAll("");
		return texto;
	}
	
	private static String removerCaracteresEspeciais(String texto){
		return texto.replaceAll(remover, "");
	}
	
	private static String removerEspacosEmBranco(String nome){
		String[] valores = nome.split(" ");
        nome = "";
        for (String s : valores) {
            if (!s.trim().equals("")) {
                nome = nome + (s.substring(0, 1).toUpperCase()) + s.substring(1);
            }
        }
        return nome;
	}
	
	/*Remove os espaços em branco do início e do fim da String;
	 *Remove os acentos e substitui pelo caracetere utf-8 correspondente;
	 *Retira os espaços em branco e transforma a primeira letra da String seguinte em maiúsculo;
	 *Remove os caracteres especiais do meio da String; 
	 * */
	private static String normalizar(String nome){
		nome = nome.trim();
		nome = removerAcentos(nome);
		nome = removerEspacosEmBranco(nome);
		nome = nome.substring(0,1) + removerCaracteresEspeciais(nome.substring(1,nome.length()));
		return nome;
	}
	
	public static String normalizarNomeClassificador(String nome){
		nome = normalizar(nome);
		nome = nome.substring(0,1).toUpperCase()+nome.substring(1,nome.length());
		return nome;
	}
	
	public static String normalizarNomeAtributo(String nome){
		nome = normalizar(nome);
		nome = nome.substring(0,1).toLowerCase()+nome.substring(1,nome.length());
		return nome;
	}
	
	public static String normalizarNomeMetodo(String nome){
		return normalizar(nome);
	}
}
