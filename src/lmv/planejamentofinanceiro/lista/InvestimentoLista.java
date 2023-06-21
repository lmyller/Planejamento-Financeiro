package lmv.planejamentofinanceiro.lista;

import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.enumeracao.TipoDadoPesquisaInvestimento;
import lmv.planejamentofinanceiro.interfaces.DadosLista;
import lmv.planejamentofinanceiro.modelo.Investimento;

public class InvestimentoLista implements DadosLista<Investimento, TipoDadoPesquisaInvestimento> {

	private List<Investimento> investimentos;
	
	public InvestimentoLista() {
		criar();
	}
	
	@Override
	public void criar() {
		investimentos = new ArrayList<>();
	}

	@Override
	public boolean inserir(Investimento investimento) {
		if (investimento != null)
			return investimentos.add(investimento);
		
		return false;
	}
	
	@Override
	public int pesquisar(String string, TipoDadoPesquisaInvestimento tipoDado) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int pesquisar(int codigo) {
		for (int indice = 0; indice < tamanhoLista(); indice++)
			if (obter(indice).getCodigo() == codigo)
				return indice;
		
		return -1;
	}

	@Override
	public Investimento obter(int indice) {
		return investimentos.get(indice);
	}

	@Override
	public boolean alterar(int indice, Investimento investimento) {
		return investimentos.set(indice, investimento) != null ? true : false;
	}

	@Override
	public boolean alterar(Investimento investimento) {
		int indice = pesquisar(investimento.getCodigo());
		
		return indice != -1 ? alterar(indice, investimento) : false;
	}

	@Override
	public int tamanhoLista() {
		return investimentos.size();
	}
}
