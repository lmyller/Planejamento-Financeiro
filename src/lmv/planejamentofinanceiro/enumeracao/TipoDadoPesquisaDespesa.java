package lmv.planejamentofinanceiro.enumeracao;

public enum TipoDadoPesquisaDespesa {
	DATA("Data"),
	DESCRICAO("Descrição"),
	VALOR("Valor");
	
	private String tipoDado;

	private TipoDadoPesquisaDespesa(String tipoDado) {
		this.tipoDado = tipoDado;
	}

	public String getTipoDado() {
		return tipoDado;
	}
}
