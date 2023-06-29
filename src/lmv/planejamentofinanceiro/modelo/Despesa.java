package lmv.planejamentofinanceiro.modelo;

public class Despesa {
	private Integer codigo;
	private String descricao;
	private Categoria categoria;
	
	public Despesa() { }

	public Despesa(String descricao, Categoria categoria) {
		this.descricao = descricao;
		this.categoria = categoria;
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
