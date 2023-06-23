package lmv.planejamentofinanceiro;

import java.time.MonthDay;
import java.time.format.DateTimeFormatter;

import javax.swing.UIManager;

import lmv.planejamentofinanceiro.gui.IgPlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;
import lmv.planejamentofinanceiro.lista.RendaMensalLista;
import lmv.planejamentofinanceiro.modelo.Categoria;
import lmv.planejamentofinanceiro.modelo.Despesa;
import lmv.planejamentofinanceiro.modelo.FormaPagamento;
import lmv.planejamentofinanceiro.modelo.Investimento;
import lmv.planejamentofinanceiro.modelo.Orcamento;
import lmv.planejamentofinanceiro.modelo.RendaMensal;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import lmv.planejamentofinanceiro.validacao.ValidacaoFormaPagamento;
import mos.reader.Line;

public class PlanejamentoFinanceiro {
	
	public static final String TITULO = "Planejamento Financeiro",
											PT = "pt",
											BR = "BR";
											
	public static void main(String[] args) {
		alterarLookAndFell("Nimbus");
		new PlanejamentoFinanceiro();
	}
	
	public PlanejamentoFinanceiro() {
		OrcamentoLista orcamentoLista = new OrcamentoLista();
		InvestimentoLista investimentoLista = new InvestimentoLista();
		RendaMensalLista rendas = new RendaMensalLista();
		
		new IgPlanejamentoFinanceiro(orcamentoLista, investimentoLista, rendas);
	}
	
	private static void alterarLookAndFell(String lookAndFeel) {
		for (var look : UIManager.getInstalledLookAndFeels()) {
			if (look.getName().equalsIgnoreCase(lookAndFeel)) {
				try {
					UIManager.setLookAndFeel(look.getClassName());
				} catch (Exception e){
					
				}
			}
		}		
	}
	
	public static Orcamento criarOrcamento(Line linha, Float valor) {
		return new Orcamento(Short.parseShort(String.valueOf(MonthDay.parse(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), 
																			DateTimeFormatter.ofPattern(ValidacaoData.REGEX_DATA_DIA_MES)).getMonthValue())),
											   ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_DATA_DESPESA), ValidacaoData.REGEX_DATA_COMPLETA), 
											   ValidacaoData.formatarMesAno(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), ValidacaoData.REGEX_DATA_DIA_MES), 
											   valor, criarDespesa(linha), criarFormaPagamento(linha), linha.quantityOfData() == Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_DESPESA ? true : false);
	}

	public static FormaPagamento criarFormaPagamento(Line linha) {
		return new FormaPagamento(ValidacaoFormaPagamento.descricaoFormaPagamento(linha.getData(Arquivo.COLUNA_FORMA_PAGAMENTO)));
	}

	public static Despesa criarDespesa(Line linha) {
		return new Despesa(linha.getData(Arquivo.COLUNA_DESCRICAO), criarCategoria(linha));
	}

	public static Categoria criarCategoria(Line linha) {
		return new Categoria(linha.getData(Arquivo.COLUNA_CATEGORIA));
	}

	public static Investimento criarInvestimento(Line linha, Float valorInvestido, Float posicao, Float rendimentoBruto,
			Float rentabilidade) {
		return new Investimento(linha.getData(Arquivo.COLUNA_OBJETIVO), linha.getData(Arquivo.COLUNA_ESTRATEGIA), 
												  linha.getData(Arquivo.COLUNA_NOME), valorInvestido, posicao, rendimentoBruto, rentabilidade,
												  ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_VENCIMENTO), ValidacaoData.REGEX_DATA_COMPLETA));
	}

	public static RendaMensal criarRenda(Line linha, Float valor) {
		return new RendaMensal(linha.getData(Arquivo.COLUNA_TIPO), 
												  ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_DATA), ValidacaoData.REGEX_DATA_COMPLETA), valor);
	}
}
