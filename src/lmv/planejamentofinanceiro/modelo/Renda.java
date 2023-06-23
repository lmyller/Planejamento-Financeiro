package lmv.planejamentofinanceiro.modelo;

public abstract class Renda {
	private final int CODIGO;
	private String descricao;
	private static int gerarCodigo;
	
	public Renda() {
		CODIGO = ++gerarCodigo;
	}

	public Renda(String descricao) {
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

	@Override
	public String toString() {
		return String.format("codigo = %s, descricao = %s", CODIGO, descricao);
	}
}	
