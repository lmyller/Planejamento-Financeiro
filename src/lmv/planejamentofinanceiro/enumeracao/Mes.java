package lmv.planejamentofinanceiro.enumeracao;

public enum Mes {
	JANEIRO("Janeiro", (short)1),
	FEVEREIRO("Fevereiro", (short)2),
	MARCO("Março", (short)3),
	ABRIL("Abril", (short)4),
	MAIO("Maio", (short)5),
	JUNHO("Junho", (short)6),
	JULHO("Julho", (short)7),
	AGOSTO("Agosto", (short)8),
	SETEMBRO("Setembro", (short)9),
	OUTUBRO("Outubro", (short)10),
	NOVEMBRO("Novembro", (short)11),
	DEZEMBRO("Dezembro", (short)12);
	
	private String nomeMes;
	private Short numeroMes;
	
	private Mes(String nomeMes, Short numeroMes) {
		this.nomeMes = nomeMes;
		this.numeroMes = numeroMes;
	}

	public String getNomeMes() {
		return nomeMes;
	}

	public Short getNumeroMes() {
		return numeroMes;
	}
}
