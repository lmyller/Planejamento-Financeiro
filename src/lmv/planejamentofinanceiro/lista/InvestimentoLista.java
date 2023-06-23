package lmv.planejamentofinanceiro.lista;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DadosLista;
import lmv.planejamentofinanceiro.modelo.Investimento;

public class InvestimentoLista implements DadosLista<Investimento>, Iterable<Investimento> {

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

	@Override
	public Iterator<Investimento> iterator() {
		return investimentos.iterator();
	}
	
	public boolean pesquisar(String estrategia, LocalDate dataVencimento) {
		for (var investimento : investimentos) 
			if (investimento.getEstrategia().equalsIgnoreCase(estrategia) && dataVencimento.isEqual(investimento.getVencimento()))
				return true;
		
		return false;
	}
}
