package org.eclipse.acceleo.module.umlToJpa.controle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.acceleo.module.umlToJpa.exception.NomeInvalidoException;
import org.eclipse.acceleo.module.umlToJpa.exception.TipoInvalidoException;
import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.modelo.Classe;
import org.eclipse.acceleo.module.umlToJpa.modelo.Operacao;
import org.eclipse.acceleo.module.umlToJpa.util.Erro;
import org.eclipse.acceleo.module.umlToJpa.validacao.ValidadorDeNomes;

public class ClasseControlador {

	private InheritanceControlador ic = InheritanceControlador.getInstance();
	private TemporalControlador tc = TemporalControlador.getInstance();
	private AtributoControlador ac = new AtributoControlador();
	private OperacaoControlador oc = new OperacaoControlador();
	private ValidadorDeNomes vn = new ValidadorDeNomes();

	public List<Erro> verificarErrosClasse(Classe c) {
		List<Erro> erros = new ArrayList<>();
		try {
			vn.isClassifierName(c.getNome());
		} catch (NomeInvalidoException e) {
			erros.add(new Erro(c.getId(), "Class", e.getMessage()));
		}
		erros.addAll(validarAtributos(c));
		erros.addAll(validarMetodos(c));
		return erros;
	}

	private List<Erro> validarAtributos(Classe classe) {
		List<Erro> erros = new ArrayList<>();
		for (Atributo a : classe.getAtributos()) {
			try {
				vn.isJavaPropertyName(a.getNome());
				if(a.getTipo()!=null){
				if (ac.isAtributoTemporal(a)) {
					a.addAnotacao(tc.getTemporal("TIMESTAMP"));//Adiciona a anotação @Temporal
				}
				}else{
					throw new TipoInvalidoException("Atributo "+a.getNome()+" na classe "+classe.getId(), "");
				}
			} catch (TipoInvalidoException | NomeInvalidoException e) {
				erros.add(new Erro(classe.getId(),"Property",e.getMessage()));
			}
		}
		return erros;
	}
	
	private List<Erro> validarMetodos(Classe classe){
		List<Erro> erros = new ArrayList<Erro>();
		for(Operacao metodo:classe.getMetodos()){
			try{
			vn.isJavaOperationName(metodo.getNome());
			}catch(NomeInvalidoException e){
				erros.add(new Erro(classe.getId(),"Operation",e.getMessage()));
			}
			for(Atributo parametro:metodo.getParametros()){
				try{
				vn.isJavaPropertyName(parametro.getNome());
				if(parametro.getTipo()==null){
					throw new TipoInvalidoException("Parametro "+parametro.getNome()+" no método "+metodo.getNome()+". Classe: "+classe.getId(), "");
				}
				}catch(NomeInvalidoException | TipoInvalidoException e){
					erros.add(new Erro(parametro.getNome(), "Param", e.getMessage()));
				}
			}
		}
		return erros;
	}

	private Set<String> gerarImports(Classe classe) {
		Set<String> retorno = new HashSet<>();
		for (Anotacao anotacao : classe.getAnotacoes()) {
			retorno.add(anotacao.getAnotacao().getName());
			for (Propriedade p : anotacao.getPropriedades()) {
				retorno.add(p.getReferencia().getName());
			}
		}
		for (Atributo att : classe.getAtributos()) {
			String p = att.getTipo().getPacote();
			
			if(att.isMultivalorado()){
				retorno.add(att.getLista().getTipoLista().getPacote()+"."+att.getLista().getTipoLista().getNomeClasse());
				if(att.getLista().getImplementacao()!=null){
					retorno.add(att.getLista().getImplementacao().getPacote()+"."+att.getLista().getImplementacao().getNomeClasse());	
				}
			}
			if (!p.equals("java.lang") && !p.equals("root")) {
				retorno.add(att.getTipo().toString());
			}
			for (Anotacao anotacao : att.getAnotacoes()) {
				retorno.add(anotacao.getAnotacao().getName());
				for(Propriedade prop: anotacao.getPropriedades()){
					if(prop.getReferencia()!=null){
						retorno.add(prop.getReferencia().getName());
					}
				}
			}
		}
		return retorno;
	}
	
	public StringBuilder gerarCodigo(Classe classe){
		StringBuilder builder = new StringBuilder();
		builder.append(gerarCabecalho(classe));
		builder.append(gerarConstrutor(classe));
		builder.append(gerarAtributos(classe));
		builder.append(gerarMetodos(classe));
		builder.append("\n}");
		return builder;
	}

	private String gerarCabecalho(Classe classe) {
		String retorno = "package modelo;\n\n";
		for (String s : gerarImports(classe)) {
			s = "import "+s;
			retorno = retorno + s + ";\n";
		}
		for (Anotacao a : classe.getAnotacoes()) {
			retorno = retorno + "\n" + a;
		}
		if (classe.getSuperclasse() != null) {
			retorno = retorno + "\npublic class " + classe.getNome()
					+ " extends " + classe.getSuperclasse().getNome() + "{";
		} else {
			retorno = retorno + "\npublic class " + classe.getNome() + "{";
		}
		return retorno;
	}
	
	private String gerarConstrutor(Classe classe){
		return "\n\npublic "+ classe.getNome() +" () {\n}";
	}

	private String gerarAtributos(Classe classe) {
		Atributo id = null;
		String atributos = "";
		String getSet = "";
		for (Atributo atributo : classe.getAtributos()) {
			if (atributo.getNome().equals("id")) {
				id = atributo;
			} else {
				atributos = atributos + ac.gerarDefinicao(atributo);
			}
			getSet = getSet + ac.gerarGet(atributo);
			getSet = getSet + ac.gerarSet(atributo);
		}
		if(id!=null){
			atributos = ac.gerarDefinicao(id)+atributos;
		}
		return atributos + "\n" + getSet;
	}
	
	private String gerarMetodos(Classe classe){
		String retorno="";
		for(Operacao metodo:classe.getMetodos()){
			retorno = retorno +"\n\n"+oc.gerarDefinicao(metodo);
		}
		return retorno;
	}
	

	public void addAnotacaoEntityId(Classe c, String tipoHeranca) {
		if (c.getSuperclasse() == null && c.isPossuiSubClasses()) {
			c.addAtributo(IdControlador.gerarId());
			c.addAnotacao(EntityControlador.getBase());
			c.addAnotacao(ic.getInheritance(tipoHeranca));//Pode dar um erro
		} else if (!c.isPossuiSubClasses() && c.getSuperclasse() != null) {
			c.addAnotacao(EntityControlador.getBase());
		} else {
			c.addAtributo(IdControlador.gerarId());
			c.addAnotacao(EntityControlador.getBase());
		}
	}
}
