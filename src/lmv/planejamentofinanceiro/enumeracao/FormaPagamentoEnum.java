package lmv.planejamentofinanceiro.enumeracao;

public enum FormaPagamentoEnum {
	CARTAO_CREDITO("Cartão de Crédito", "CC"),
	CARTAO_DEBITO("Cartão de Débito", "CD"),
	DINHEIRO("Dinheiro", "Dinheiro"),
	PIX("Pix", "Pix");
	
	private String descricaoPagamento, siglaPagamento;
	
	private FormaPagamentoEnum(String descricaoPagamento, String siglaPagamento) {
		this.descricaoPagamento = descricaoPagamento;
		this.siglaPagamento = siglaPagamento;
	}

	public String getDescricaoPagamento() {
		return descricaoPagamento;
	}

	public String getSiglaPagamento() {
		return siglaPagamento;
	}
}
