package org.eclipse.acceleo.module.umlToJpa.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GerenciadorDeArquivosJava {

	private File diretorio;
	
	public void setDiretorio(File diretorio){
		if(diretorio.isDirectory()){
			this.diretorio = diretorio;
		}
	}
	
	public void criarArquivo(String nomeClasse,StringBuilder conteudo){
		try {
			File arquivo = new File(diretorio.getAbsolutePath()+"//"+nomeClasse+".java");
			FileWriter fw = new FileWriter(arquivo,false);
			BufferedWriter bf = new BufferedWriter(fw);
			bf.write(conteudo.toString());
			bf.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
