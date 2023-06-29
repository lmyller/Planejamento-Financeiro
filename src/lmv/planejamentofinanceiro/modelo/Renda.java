package lmv.planejamentofinanceiro.modelo;

public abstract class Renda {
	private Integer codigo;
	private String descricao;
	
	public Renda() {}

	public Renda(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return String.format("codigo = %s, descricao = %s", codigo, descricao);
	}
}	
