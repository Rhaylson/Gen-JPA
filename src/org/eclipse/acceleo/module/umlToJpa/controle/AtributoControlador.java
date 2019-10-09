package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.util.GerenciadorDeTipos;

public class AtributoControlador {

	public String gerarDefinicao(Atributo a){
		String retorno="";
		for(Anotacao anotacao:a.getAnotacoes()){
			retorno = retorno +"\n     "+anotacao;
		}
		return retorno+"\n     "+a.getVisibilidade()+" "+processarTipo(a)+" "+a.getNome()+";";
	}
	
	private String processarTipo(Atributo a){
		if(a.isMultivalorado()){
		 return a.getLista().getTipoLista().getNomeClasse()+"<"+a.getTipo().getNomeClasse()+">";
		}
		return a.getTipo().getNomeClasse();
	}
	
	public String gerarGet(Atributo a) {
		String nome = a.getNome();
		return "\n     public " + processarTipo(a) + " get"
				+ nome.substring(0, 1).toUpperCase()
				+ nome.substring(1, nome.length()) + " (){\n"
				+ "\t\treturn this." + a.getNome() + ";\n     }\n";
	}

	public String gerarSet(Atributo a) {
		String nome = a.getNome();
		return "\n     public void set" + nome.substring(0, 1).toUpperCase()
				+ nome.substring(1, nome.length()) + " ("
				+ processarTipo(a) + " " + nome + "){\n"
				+ "\t\tthis." + nome + " = " + nome + ";\n     }\n";
	}
	
	public boolean isAtributoTemporal(Atributo a){
		if(a.getTipo().getNomeClasse().equals("Calendar")|| a.getTipo().getNomeClasse().equals("Date")){
			return true;
		}
		return false;
	}
}
