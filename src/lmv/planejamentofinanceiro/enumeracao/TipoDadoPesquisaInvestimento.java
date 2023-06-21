package lmv.planejamentofinanceiro.enumeracao;

public enum TipoDadoPesquisaInvestimento {
	OBJETIVO("Objetivo"),
	NOME("Nome");
	
	private String tipoDado;

	private TipoDadoPesquisaInvestimento(String tipoDado) {
		this.tipoDado = tipoDado;
	}

	public String getTipoDado() {
		return tipoDado;
	}
}
