package lmv.planejamentofinanceiro.modelo;

import java.time.LocalDate;

public class Investimento {
	private int codigo;
	private String objetivo, nome, estrategia;
	private Float valorInvestido, posicao, rendimentoBruto, rentabilidade;
	private LocalDate vendimento;
	
	public Investimento(int codigo, String objetivo, String nome, String estrategia, Float valorInvestido,
			Float posicao, Float rendimentoBruto, Float rentabilidade, LocalDate vendimento) {
		this.codigo = codigo;
		this.objetivo = objetivo;
		this.nome = nome;
		this.estrategia = estrategia;
		this.valorInvestido = valorInvestido;
		this.posicao = posicao;
		this.rendimentoBruto = rendimentoBruto;
		this.rentabilidade = rentabilidade;
		this.vendimento = vendimento;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
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

	public LocalDate getVendimento() {
		return vendimento;
	}

	public void setVendimento(LocalDate vendimento) {
		this.vendimento = vendimento;
	}

	@Override
	public String toString() {
		return String.format(
				"codigo = %s, objetivo = %s, nome = %s, estrategia = %s, valorInvestido = %s, posicao = %s, rendimentoBruto = %s, rentabilidade = %s, vendimento = %s",
				codigo, objetivo, nome, estrategia, valorInvestido, posicao, rendimentoBruto, rentabilidade,
				vendimento);
	}
}
