package lmv.planejamentofinanceiro.modelo;

import java.time.LocalDate;

public class RendaMensal extends Renda{
	private LocalDate data;
	private Double valor;
	
	public RendaMensal() {}
	
	public RendaMensal(String descricao) {
		super(descricao);
	}

	public RendaMensal(String descricao, LocalDate data, Double valor) {
		super(descricao);
		this.data = data;
		this.valor = valor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
}
