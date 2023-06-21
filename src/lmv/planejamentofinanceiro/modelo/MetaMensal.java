package lmv.planejamentofinanceiro.modelo;

public class MetaMensal {
	private String mesAnoMeta;
	private Float valor, percentual;
	private Categoria categoria;
	
	public MetaMensal(String mesAnoMeta, Float valor, Float percentual, Categoria categoria) {
		this.mesAnoMeta = mesAnoMeta;
		this.valor = valor;
		this.percentual = percentual;
		this.categoria = categoria;
	}

	public String getMesAnoMeta() {
		return mesAnoMeta;
	}

	public void setMesAnoMeta(String mesAnoMeta) {
		this.mesAnoMeta = mesAnoMeta;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Float getPercentual() {
		return percentual;
	}

	public void setPercentual(Float percentual) {
		this.percentual = percentual;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
