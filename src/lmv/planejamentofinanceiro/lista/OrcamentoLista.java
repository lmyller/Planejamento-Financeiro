package lmv.planejamentofinanceiro.lista;

import static mos.es.InputOutput.showError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.enumeracao.TipoDadoPesquisaDespesa;
import lmv.planejamentofinanceiro.interfaces.DadosLista;
import lmv.planejamentofinanceiro.modelo.Orcamento;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;

public class OrcamentoLista implements DadosLista<Orcamento>, Iterable<Orcamento>{

	private List<Orcamento> orcamentos;
	
	public OrcamentoLista() {
		criar();
	}

	@Override
	public void criar() {
		orcamentos = new ArrayList<>();
	}

	@Override
	public boolean inserir(Orcamento orcamento) {
		if (orcamento != null)
			return orcamentos.add(orcamento);
		
		return false;
	}

	@Override
	public int pesquisar(int codigo) {
		for (int indice = 0; indice < tamanhoLista(); indice++)
			if (obter(indice).getDespesa().getCodigo() == codigo)
				return indice;
		
		return -1;
	}

	public int pesquisar(String stringPesquisada, TipoDadoPesquisaDespesa tipoDado) {
		if (tipoDado == TipoDadoPesquisaDespesa.DATA)
			return pesquisarData(stringPesquisada);
		
		if (tipoDado == TipoDadoPesquisaDespesa.DESCRICAO)
			return pesquisarDescricao(stringPesquisada);
		
		if (tipoDado == TipoDadoPesquisaDespesa.VALOR)
			return pesquisarValor(stringPesquisada);
				
		return -1;
	}

	private int pesquisarValor(String stringPesquisada) {
		try {
			Float valor = Float.parseFloat(stringPesquisada);
			
			for (int indice = 0; indice < tamanhoLista(); indice++)
				if (obter(indice).getValor() == valor)
					return indice;
			
			return -1;
		} catch (NumberFormatException | NullPointerException exception) {
			showError(VALOR_INVALIDO, PlanejamentoFinanceiro.TITULO);
			return -1;
		}
	}

	private int pesquisarDescricao(String stringPesquisada) {
		for (int indice = 0; indice < tamanhoLista(); indice++)
			if (obter(indice).getDespesa().getDescricao().equalsIgnoreCase(stringPesquisada))
				return indice;
		
		return -1;
	}

	private int pesquisarData(String stringPesquisada) {
		LocalDate localDate;
		
		if (new ValidacaoData().valida(stringPesquisada)) {
			localDate = LocalDate.parse(stringPesquisada, DateTimeFormatter.ofPattern(ValidacaoData.REGEX_DATA_COMPLETA));
			
			for (int indice = 0; indice < tamanhoLista(); indice++)
				if (localDate.isEqual(obter(indice).getDataDespesa()))
					return indice;
		}
		else
			showError(ValidacaoData.DATA_INVALIDA, PlanejamentoFinanceiro.TITULO);

		return -1;
	}

	@Override
	public Orcamento obter(int indice) {
		return orcamentos.get(indice);
	}

	@Override
	public boolean alterar(int indice, Orcamento orcamento) {
		return orcamentos.set(indice, orcamento) != null ? true : false;
	}

	@Override
	public boolean alterar(Orcamento orcamento) {
		int indice = pesquisar(orcamento.getDespesa().getCodigo());
	
		return indice != -1 ? alterar(indice, orcamento) : false;
	}

	@Override
	public int tamanhoLista() {
		return orcamentos.size();
	}

	@Override
	public Iterator<Orcamento> iterator() {
		return orcamentos.iterator();
	}
}
