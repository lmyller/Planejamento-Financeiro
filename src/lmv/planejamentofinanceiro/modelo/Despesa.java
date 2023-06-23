package lmv.planejamentofinanceiro.modelo;

public class Despesa {
	private final int CODIGO;
	private String descricao;
	private Categoria categoria;
	
	private static int gerarCodigo;
	
	private Despesa() {
		CODIGO = ++gerarCodigo;
	}

	public Despesa(String descricao, Categoria categoria) {
		this();
		this.descricao = descricao;
		this.categoria = categoria;
	}

	public int getCodigo() {
		return CODIGO;
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
