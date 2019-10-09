package org.eclipse.acceleo.module.umlToJpa.controle;

import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.modelo.Operacao;

public class OperacaoControlador {

	public String gerarDefinicao(Operacao metodo){
		return metodo.getVisibilidade()+" "+tipoRetorno(metodo)+" "+metodo.getNome()+gerarParametros(metodo)+"{\n"+gerarRetorno(metodo)+"\n}";
	}
	
	private String tipoRetorno(Operacao metodo){
		if(!metodo.isVoid()){
			return metodo.getRetorno().getNomeClasse();
		}
		return "void";
	}
	
	private String gerarRetorno(Operacao metodo){
		if(!metodo.isVoid()){
			if(metodo.getRetorno()!=null){
			return "return "+metodo.getRetorno().getValorDefault()+";";
			}else{
				return "return null;";
			}
		}
		return "";
	}
	
	private String gerarParametros(Operacao metodo){
		String retorno="";
		for(Atributo parametro:metodo.getParametros()){
			retorno = retorno + parametro.getTipo().getNomeClasse() +" "+parametro.getNome()+",";
		}
		if(retorno.length()>0){
			retorno = retorno.substring(0, retorno.length()-1);
		}
		return "("+retorno+")";
	}
}
