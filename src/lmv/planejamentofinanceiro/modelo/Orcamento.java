package lmv.planejamentofinanceiro.modelo;

import java.time.LocalDate;

public class Orcamento {
	private String mesAno;
	private LocalDate dataDespesa, dataPagamento;
	private Float valor;
	private Despesa despesa;
	private FormaPagamento formaPagamento;
	
	public Orcamento(String mesAno, LocalDate dataDespesa, LocalDate dataPagamento, Float valor, Despesa despesa,
			FormaPagamento formaPagamento) {
		this.mesAno = mesAno;
		this.dataDespesa = dataDespesa;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.despesa = despesa;
		this.formaPagamento = formaPagamento;
	}

	public String getMesAno() {
		return mesAno;
	}

	public void setMesAno(String mesAno) {
		this.mesAno = mesAno;
	}

	public LocalDate getDataDespesa() {
		return dataDespesa;
	}

	public void setDataDespesa(LocalDate dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
}
