package lmv.planejamentofinanceiro.modelo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.interfaces.Tolist;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import lmv.planejamentofinanceiro.validacao.ValidacaoFormaPagamento;

public class Orcamento implements Tolist<Orcamento>{
	private short mesAno;
	private LocalDate dataDespesa;
	private MonthDay dataPagamento;
	private Double valor;
	private Despesa despesa;
	private FormaPagamento formaPagamento;
	private boolean situacao;
	
	public Orcamento(short mesAno, Despesa despesa, LocalDate dataDespesa, MonthDay dataPagamento, FormaPagamento formaPagamento, Double valor, 
			 boolean situacao) {
		this.mesAno = mesAno;
		this.dataDespesa = dataDespesa;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.despesa = despesa;
		this.formaPagamento = formaPagamento;
		this.situacao = situacao;
	}

	public short getMesAno() {
		return mesAno;
	}

	public void setMesAno(short mesAno) {
		this.mesAno = mesAno;
	}

	public LocalDate getDataDespesa() {
		return dataDespesa;
	}

	public void setDataDespesa(LocalDate dataDespesa) {
		this.dataDespesa = dataDespesa;
	}

	public MonthDay getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(MonthDay dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
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

	public boolean isSituacao() {
		return situacao;
	}

	public void setSituacao(boolean situacao) {
		this.situacao = situacao;
	}

	@Override
	public Object[] toList() {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		
		return new Object[] {getDataDespesa().format(DateTimeFormatter.ofPattern(ValidacaoData.DATA_COMPLETA)), getDataPagamento().getDayOfMonth(),
										   ValidacaoFormaPagamento.siglaFormaPagamento(getFormaPagamento().getDescricao()), getDespesa().getDescricao(), formatPreco.format(getValor()), 
										   isSituacao()};
	}
}
