package lmv.planejamentofinanceiro.modelo;

public class Despesa {
	private int codigo;
	private String descricao;
	private Categoria categoria;
	
	public Despesa(int codigo, String descricao, Categoria categoria) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.categoria = categoria;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
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
