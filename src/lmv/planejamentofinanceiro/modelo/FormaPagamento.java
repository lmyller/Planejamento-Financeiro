package lmv.planejamentofinanceiro.modelo;

public class FormaPagamento {
	
	private Integer codigo;
	private String descricao;
	
	public FormaPagamento() {}

	public FormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo  = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
