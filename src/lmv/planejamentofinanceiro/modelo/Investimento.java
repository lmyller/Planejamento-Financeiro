package lmv.planejamentofinanceiro.modelo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.interfaces.Tolist;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;

public class Investimento implements Tolist<Investimento> {
	private Integer codigo;
	private String objetivo, nome, estrategia;
	private Double valorInvestido, posicao, rendimentoBruto, rentabilidade;
	private LocalDate vencimento;
	
	public Investimento() { }

	public Investimento(String objetivo, String estrategia, String nome, Double valorInvestido,
			Double posicao, Double rendimentoBruto, Double rentabilidade, LocalDate vencimento) {
		this.objetivo = objetivo;
		this.nome = nome;
		this.estrategia = estrategia;
		this.valorInvestido = valorInvestido;
		this.posicao = posicao;
		this.rendimentoBruto = rendimentoBruto;
		this.rentabilidade = rentabilidade;
		this.vencimento = vencimento;
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}

	public Double getValorInvestido() {
		return valorInvestido;
	}

	public void setValorInvestido(Double valorInvestido) {
		this.valorInvestido = valorInvestido;
	}

	public Double getPosicao() {
		return posicao;
	}

	public void setPosicao(Double posicao) {
		this.posicao = posicao;
	}

	public Double getRendimentoBruto() {
		return rendimentoBruto;
	}

	public void setRendimentoBruto(Double rendimentoBruto) {
		this.rendimentoBruto = rendimentoBruto;
	}

	public Double getRentabilidade() {
		return rentabilidade;
	}

	public void setRentabilidade(Double rentabilidade) {
		this.rentabilidade = rentabilidade;
	}

	public LocalDate getVencimento() {
		return vencimento;
	}

	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}

	@Override
	public String toString() {
		return String.format(
				"codigo = %s, objetivo = %s, nome = %s, estrategia = %s, valorInvestido = %s, posicao = %s, rendimentoBruto = %s, rentabilidade = %s, vencimento = %s",
				codigo, objetivo, nome, estrategia, valorInvestido, posicao, rendimentoBruto, rentabilidade,
				vencimento);
	}

	@Override
	public Object[] toList() {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		NumberFormat formatPercentual = NumberFormat.getPercentInstance();
		
		return new Object[] {getObjetivo(), getEstrategia(), getNome(), formatPreco.format(getValorInvestido()), formatPreco.format(posicao), 
										   formatPreco.format(rendimentoBruto), formatPercentual.format(getRentabilidade()), 
										   getVencimento().format(DateTimeFormatter.ofPattern(ValidacaoData.DATA_COMPLETA))};
	}
}
