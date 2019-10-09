package org.eclipse.acceleo.module.umlToJpa.util;

import java.io.FileWriter;
import java.io.IOException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class LogDeErros {

	private static final String URL_ARQUIVO = "C:\\Users\\Rhaylson\\OneDrive\\workspace\\org.eclipse.acceleo.module.umlToJpa\\xmls\\log.txt";
	private XMLOutputter xmlOut;
	private FileWriter fw;
	private Element raiz;
	private Document documento;

	public LogDeErros() {
		try {
			fw = new FileWriter(URL_ARQUIVO);
			Element e = new Element("Erros");
			xmlOut = new XMLOutputter();
			raiz = new Element("erros");
			documento = new Document(e);
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar o arquivo de Log!");
		}
	}
	
	public void addErro(Erro e){
		Element erro = new Element("Erro");
		Element nome = new Element("elemento");
		Element tipo = new Element("tipo");
		Element desc = new Element("descricaoErro");
		
		nome.setText(e.getElemento());
		tipo.setText(e.getTipo());
		desc.setText(e.getMensagem());
		
		erro.addContent("\n");
		erro.addContent(nome);
		erro.addContent("\n");
		erro.addContent(tipo);
		erro.addContent("\n");
		erro.addContent(desc);
		erro.addContent("\n");
		raiz.addContent(erro);
	}
	
	public void gravarConteudoNoArquivo(){
		try{
			documento.setRootElement(raiz);
			xmlOut.output(documento,fw);
			fw.close();
		}catch(IOException e){
			throw new RuntimeException("Erro ao gravar dados no arquivo de log.");
		}
	}
}
