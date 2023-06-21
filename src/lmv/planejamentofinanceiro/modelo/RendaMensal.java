package lmv.planejamentofinanceiro.modelo;

import java.time.LocalDate;

public class RendaMensal{
	private LocalDate data;
	private Float valor;
	private Renda renda;
	
	public RendaMensal(LocalDate data, Float valor, Renda renda) {
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

	public Renda getRenda() {
		return renda;
	}

	public void setRenda(Renda renda) {
		this.renda = renda;
	}
}
