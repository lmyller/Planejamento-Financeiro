package lmv.planejamentofinanceiro.modelo;

public class FormaPagamento {
	
	private String tipoFormaPagamento, descricao;

	private FormaPagamento(String tipoFormaPagamento, String descricao) {
		this.tipoFormaPagamento = tipoFormaPagamento;
		this.descricao = descricao;
	}

	public String getTipoFormaPagamento() {
		return tipoFormaPagamento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setTipoFormaPagamento(String tipoFormaPagamento) {
		this.tipoFormaPagamento = tipoFormaPagamento;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
