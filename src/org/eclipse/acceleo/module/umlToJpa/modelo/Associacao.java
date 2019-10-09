package org.eclipse.acceleo.module.umlToJpa.modelo;

public class Associacao {

	private String nome;
	private String idMembroA;
	private String idMembroB;
	private Membro membroA;
	private Membro membroB;
	private String multiplicidade;
	private boolean check;

	public String getIdMembroA() {
		return idMembroA;
	}

	public void setIdMembroA(String idMembroA) {
		this.idMembroA = idMembroA;
	}

	public String getIdMembroB() {
		return idMembroB;
	}

	public void setIdMembroB(String idMembroB) {
		this.idMembroB = idMembroB;
	}

	public Membro getMembroA() {
		return membroA;
	}

	public void setMembroA(Membro membroA) {
		this.membroA = membroA;
	}

	public Membro getMembroB() {
		return membroB;
	}

	public void setMembroB(Membro membroB) {
		this.membroB = membroB;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String normalizaMultiplicidade(String minimo, String maximo) {
		if (!minimo.equals("1")) {
			minimo = "1";
		}
		if (!maximo.equals("1")) {
			maximo = "*";
		}
		return minimo + ".." + maximo;
	}

	public void setMultiplicidade(String multiplicidade) {
		this.multiplicidade = multiplicidade;
	}

	public String getMultiplicidade() {
		return multiplicidade;
	}
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public boolean equals(Object obj) {
		Associacao a = (Associacao) obj;
		if ((this.idMembroA.equals(a.idMembroA) && this.idMembroB
				.equals(a.idMembroB))
				|| (this.idMembroB.equals(a.idMembroA) && this.idMembroA
						.equals(a.idMembroB))) {
			return true;
		}
		return false;
	}

}
