package org.eclipse.acceleo.module.umlToJpa.controle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.acceleo.module.umlToJpa.mapeamento.ManyToManyControlador;
import org.eclipse.acceleo.module.umlToJpa.mapeamento.OneToManyManyToOneControlador;
import org.eclipse.acceleo.module.umlToJpa.mapeamento.OneToOneControlador;
import org.eclipse.acceleo.module.umlToJpa.model.Anotacao;
import org.eclipse.acceleo.module.umlToJpa.model.Propriedade;
import org.eclipse.acceleo.module.umlToJpa.modelo.Associacao;
import org.eclipse.acceleo.module.umlToJpa.modelo.Atributo;
import org.eclipse.acceleo.module.umlToJpa.modelo.AtributoMultivalorado;
import org.eclipse.acceleo.module.umlToJpa.modelo.Membro;
import org.eclipse.acceleo.module.umlToJpa.util.Erro;
import org.eclipse.acceleo.module.umlToJpa.util.GerenciadorDeTipos;

public class AssociacaoControlador {

	private CascadeFetchControlador cfc = CascadeFetchControlador.getInstance();
	private GerenciadorDeTipos gt = GerenciadorDeTipos.getInstance();

	public List<Erro> processarAssociacoes(Associacao associacao,
			List<Associacao> semelhantes, String tipoLista) {
		List<Erro> erros = new ArrayList<Erro>();
		int contador = 0;
			Erro e = processarAssociacao(associacao, contador, tipoLista);
			if(e!=null){
				erros.add(e);
			}
		if (semelhantes.size() != 0) {
			for (Associacao a : semelhantes) {
					contador++;
					e = processarAssociacao(a, contador, tipoLista);
					if(e!=null){
						erros.add(e);
						contador--;
					}
				} 
			}
		return erros;
	}

	private Erro processarAssociacao(Associacao a, int contador,
			String tipoLista) {
		if (!a.isCheck()) {

			AtributoMultivalorado at = new AtributoMultivalorado();
			at.setTipoLista(gt.recuperaTipo(tipoLista));

			Atributo atributo1 = new Atributo();
			Atributo atributo2 = new Atributo();
			Anotacao a1 = null;
			Anotacao a2 = null;

			Membro mA = a.getMembroA();
			Membro mB = a.getMembroB();

			int tipo = tipoNavegacao(mA.isNavegavel(), mB.isNavegavel());
			String tipoAgregacao = tipoAgregacao(a);
	
			if (tipoAgregacao != null && tipo != -1) {

				atributo1.setVisibilidade("private");
				atributo2.setVisibilidade("private");

				atributo1.setTipo(GerenciadorDeTipos.getInstance()
						.recuperaTipo(a.getIdMembroB()));
				atributo2.setTipo(GerenciadorDeTipos.getInstance()
						.recuperaTipo(a.getIdMembroA()));

				if (contador == 0) {
					atributo1.setNome(mB.getClasse().getNome().toLowerCase());
					atributo2.setNome(mA.getClasse().getNome().toLowerCase());
				} else {
					atributo1.setNome(mB.getClasse().getNome().toLowerCase()
							+ contador);
					atributo2.setNome(mA.getClasse().getNome().toLowerCase()
							+ contador);
				}

				if (a.getMultiplicidade().equals("1..1")) {
					if (tipo == 1) {
						a1 = OneToOneControlador
								.gerarOneToOneBidirecional(atributo1.getNome());
						a2 = OneToOneControlador.gerarOneToOneUnidirecional();
					} else if (tipo == 2) {
						a1 = OneToOneControlador.gerarOneToOneUnidirecional();
					} else {
						a2 = OneToOneControlador.gerarOneToOneUnidirecional();
					}
				} else if (a.getMultiplicidade().equals("1..*")) {
					atributo1.setMultivalorado(true);
					atributo1.setLista(at);
					if (tipo == 1) {
						a1 = OneToManyManyToOneControlador
								.gerarManyToOneUnidirecional();
						a2 = OneToManyManyToOneControlador
								.oneToManyBidirecional(atributo2.getNome());
					} else if (tipo == 2) {
						a1 = OneToManyManyToOneControlador
								.gerarManyToOneUnidirecional();
					} else {
						a2 = OneToManyManyToOneControlador
								.gerarOneToManyUnidirecional();
					}
				}

				else if (a.getMultiplicidade().equals("*..1")) {
					atributo2.setMultivalorado(true);
					atributo2.setLista(at);

					if (tipo == 1) {
						a1 = OneToManyManyToOneControlador
								.oneToManyBidirecional(atributo1.getNome());
						a2 = OneToManyManyToOneControlador
								.gerarManyToOneUnidirecional();
					} else if (tipo == 2) {
						a1 = OneToManyManyToOneControlador
								.gerarOneToManyUnidirecional();
					} else {
						a2 = OneToManyManyToOneControlador
								.gerarManyToOneUnidirecional();
					}
				} else if (a.getMultiplicidade().equals("*..*")) {
					atributo1.setMultivalorado(true);
					atributo1.setLista(at);
					atributo2.setMultivalorado(true);
					atributo2.setLista(at);
					if (tipo == 1) {
						a1 = ManyToManyControlador
								.gerarManyToManyBidirecional(atributo1
										.getNome());
						a2 = ManyToManyControlador.gerarManyToMany();
					} else if (tipo == 2) {
						a1 = ManyToManyControlador.gerarManyToMany();
					} else {
						a2 = ManyToManyControlador.gerarManyToMany();
					}
				}

				if (mA.isNavegavel()) {
					if (mA.getTipoAgregacao().equals("composite")
							|| mA.getTipoAgregacao().equals("shared")) {
						a1 = anotacaoMapeamento(a1,
								retornaPropriedadeAgregacao(a));
					}
					atributo2.addAnotacao(a1);
					mB.getClasse().addAtributo(atributo2);
				}
				
				if (mB.isNavegavel()) {
					if (mB.getTipoAgregacao().equals("composite")
							|| mB.getTipoAgregacao().equals("shared")) {
						a2 = anotacaoMapeamento(a2,
								retornaPropriedadeAgregacao(a));
					}
					atributo1.addAnotacao(a2);
					mA.getClasse().addAtributo(atributo1);
				}
				a.setCheck(true);
				return null;
			} else {
				Erro erro = new Erro();
				erro.setElemento(a.getNome());
				erro.setMensagem("A associação "+a.getNome()+" entre "+a.getIdMembroA()+" e "+a.getIdMembroB()+" apresenta erros. Verifique multiplicidade, navegação e agregações.");
				erro.setTipo("Association");
				return erro;
			}
		}
		return null;
	}

	private Anotacao anotacaoMapeamento(Anotacao a, Propriedade p) {
		if (p != null) {
			a.addPropriedade(p);
		}
		return a;
	}

	private Propriedade retornaPropriedadeAgregacao(Associacao associacao) {
		Propriedade p = null;
		String agregacao = tipoAgregacao(associacao);
		
		if (agregacao.equals("compositeA")) {
			if (associacao.getMembroA().isNavegavel() && associacao.getMultiplicidade().equals("*..1")) {
				p = cfc.gerarCascade("ALL");
			}
		} else if (agregacao.equals("compositeB")) {
			if (associacao.getMembroB().isNavegavel() && associacao.getMultiplicidade().equals("1..*")) {
				p = cfc.gerarCascade("ALL");
			}
		} else if (agregacao.equals("sharedA")) {
			if (associacao.getMembroA().isNavegavel()) {
				p = cfc.gerarFetch("EAGER");
			}
		} else if (agregacao.equals("sharedB")) {
			if (associacao.getMembroB().isNavegavel()) {
				p = cfc.gerarFetch("EAGER");
			}
		}
		return p;
	}

	public String tipoMultiplicidade(Associacao a) {
		String valor = null;
		String multiplicidade1 = a.getMembroA().getMultiplicidade();
		String multiplicidade2 = a.getMembroB().getMultiplicidade();

		if (multiplicidade1.equals("1..1")) {
			if (multiplicidade2.equals("1..1")) {
				valor = "1..1";
			} else if (multiplicidade2.equals("1..*")) {
				valor = "1..*";
			}
		} else if (multiplicidade1.equals("1..*")) {
			if (multiplicidade2.equals("1..1")) {
				valor = "*..1";
			} else if (multiplicidade2.equals("1..*")) {
				valor = "*..*";
			}
		}
		return valor;
	}

	public String tipoAgregacao(Associacao associacao) {
		String retorno = null;
		String ag1 = associacao.getMembroA().getTipoAgregacao();
		String ag2 = associacao.getMembroB().getTipoAgregacao();
		if (ag1.equals("composite") && ag2.equals("none")){
			if(associacao.getMultiplicidade().equals("*..1") && associacao.getMembroA().isNavegavel()){
			retorno = "compositeA";
			}
		} else if (ag1.equals("none") && ag2.equals("composite")  && associacao.getMultiplicidade().equals("1..*")) {
			if(associacao.getMultiplicidade().equals("1..*")&& associacao.getMembroB().isNavegavel()){
			retorno = "compositeB";
			}
		} else if (ag1.equals("shared") && ag2.equals("none")) {
			if(associacao.getMembroA().isNavegavel()){
			retorno = "sharedA";
			}
		} else if (ag1.equals("none") && ag2.equals("shared")) {
			if(associacao.getMembroB().isNavegavel()){
			retorno = "sharedB";
			}
		}else if (ag1.equals("none") && ag2.equals("none")) {
			retorno = "none";
		}
		return retorno;
	}

	public int tipoNavegacao(boolean navegacao1, boolean navegacao2) {
		int tipo;
		if (navegacao1 == true && navegacao2 == true) {
			tipo = 1;// bidirecional
		} else if (navegacao1 == true && navegacao2 == false) {
			tipo = 2;// ladoA
		} else if (navegacao1 == false && navegacao2 == true) {
			tipo = 3;// ladoB
		} else {
			tipo = -1;
		}
		return tipo;
	}
}
