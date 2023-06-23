package lmv.planejamentofinanceiro.modelo;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.interfaces.Tolist;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;

public class Investimento implements Tolist<Investimento> {
	private final int CODIGO;
	private String objetivo, nome, estrategia;
	private Float valorInvestido, posicao, rendimentoBruto, rentabilidade;
	private LocalDate vencimento;
	private static int gerarCodigo;
	
	private Investimento() {
		CODIGO = ++gerarCodigo;
	}

	public Investimento(String objetivo, String nome, String estrategia, Float valorInvestido,
			Float posicao, Float rendimentoBruto, Float rentabilidade, LocalDate vencimento) {
		this();
		this.objetivo = objetivo;
		this.nome = nome;
		this.estrategia = estrategia;
		this.valorInvestido = valorInvestido;
		this.posicao = posicao;
		this.rendimentoBruto = rendimentoBruto;
		this.rentabilidade = rentabilidade;
		this.vencimento = vencimento;
	}

	public int getCodigo() {
		return CODIGO;
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

	public Float getValorInvestido() {
		return valorInvestido;
	}

	public void setValorInvestido(Float valorInvestido) {
		this.valorInvestido = valorInvestido;
	}

	public Float getPosicao() {
		return posicao;
	}

	public void setPosicao(Float posicao) {
		this.posicao = posicao;
	}

	public Float getRendimentoBruto() {
		return rendimentoBruto;
	}

	public void setRendimentoBruto(Float rendimentoBruto) {
		this.rendimentoBruto = rendimentoBruto;
	}

	public Float getRentabilidade() {
		return rentabilidade;
	}

	public void setRentabilidade(Float rentabilidade) {
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
				CODIGO, objetivo, nome, estrategia, valorInvestido, posicao, rendimentoBruto, rentabilidade,
				vencimento);
	}

	@Override
	public Object[] toList() {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		NumberFormat formatPercentual = NumberFormat.getPercentInstance();
		
		return new Object[] {getObjetivo(), getEstrategia(), getNome(), formatPreco.format(getValorInvestido()), formatPreco.format(posicao), 
										   formatPreco.format(rendimentoBruto), formatPercentual.format(getRentabilidade()), 
										   getVencimento().format(DateTimeFormatter.ofPattern(ValidacaoData.REGEX_DATA_COMPLETA))};
	}
}
