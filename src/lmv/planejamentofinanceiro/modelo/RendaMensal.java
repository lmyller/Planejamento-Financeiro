package lmv.planejamentofinanceiro.modelo;

import java.time.LocalDate;

public class RendaMensal extends Renda{
	private LocalDate data;
	private Float valor;
	
	public RendaMensal() {}

	public RendaMensal(String descricao, LocalDate data, Float valor) {
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

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
}
