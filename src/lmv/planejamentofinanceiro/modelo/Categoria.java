package lmv.planejamentofinanceiro.modelo;

public class Categoria {
	private final int CODIGO;
	private String descricao;
	
	private static int gerarCodigo;
	
	private Categoria() {
		CODIGO = gerarCodigo++;
	}

	public Categoria(String descricao) {
		this();
		this.descricao = descricao;
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
}
