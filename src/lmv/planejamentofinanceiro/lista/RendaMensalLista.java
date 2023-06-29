package lmv.planejamentofinanceiro.lista;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DadosLista;
import lmv.planejamentofinanceiro.modelo.RendaMensal;

public class RendaMensalLista implements DadosLista<RendaMensal>, Iterable<RendaMensal> {
	
	private List<RendaMensal> rendas;
	
	public RendaMensalLista() {
		criar();
	}
	
	public RendaMensalLista(List<RendaMensal> rendas) {
		this.rendas = rendas;
	}
	
	@Override
	public void criar() {
		rendas = new ArrayList<>();
	}

	@Override
	public boolean inserir(RendaMensal rendaMensal) {
		if (rendaMensal != null) 
			return rendas.add(rendaMensal);
		
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
	public RendaMensal obter(int indice) {
		return rendas.get(indice);
	}

	@Override
	public boolean alterar(int indice, RendaMensal rendaMensal) {
		return rendas.set(indice, rendaMensal) != null ? true : false;
	}

	@Override
	public boolean alterar(RendaMensal rendaMensal) {
		int indice = pesquisar(rendaMensal.getCodigo());
		
		return indice != -1 ? alterar(indice, rendaMensal) : false ;
	}

	@Override
	public int tamanhoLista() {
		return rendas.size();
	}

	@Override
	public Iterator<RendaMensal> iterator() {
		return rendas.iterator();
	}
}
