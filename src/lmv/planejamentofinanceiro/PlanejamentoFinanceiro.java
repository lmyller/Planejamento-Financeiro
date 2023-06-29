package lmv.planejamentofinanceiro;

import java.sql.SQLException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.UIManager;

import lmv.planejamentofinanceiro.bd.conexao.Conexao;
import lmv.planejamentofinanceiro.dao.RendaMensalDAO;
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
import mos.es.InputOutput;
import mos.reader.Line;

public class PlanejamentoFinanceiro {
	
	public static final String TITULO = "Planejamento Financeiro",
											PT = "pt",
											BR = "BR",
											URL_BD = "jdbc:postgresql://localhost/financialplanning",
											USUARIO_BD = "dba",
											SENHA_BD = "fpdb@",
											ERRO_CONEXAO_BANCO_DE_DADOS = "Erro na conexão com banco de dados\nO programa será finalizado",
											ERRO_SALVAR_BD = "Erro ao salvar no banco de dados";
											
	public static void main(String[] args) {
		alterarLookAndFell("Nimbus");
		new PlanejamentoFinanceiro();
	}
	
	public PlanejamentoFinanceiro() {
		Conexao conexao = null;
		OrcamentoLista orcamentoLista = new OrcamentoLista();
		InvestimentoLista investimentoLista = new InvestimentoLista();
		RendaMensalLista rendas = null;
		
		try {
			conexao = new Conexao(URL_BD, USUARIO_BD, SENHA_BD);
			
			obterRendasBD(rendas, conexao);
			
			new IgPlanejamentoFinanceiro(orcamentoLista, investimentoLista, rendas, conexao);
		} catch (SQLException e) {
			InputOutput.showError(ERRO_CONEXAO_BANCO_DE_DADOS, TITULO);;
		}
	}
	
	private void obterRendasBD(RendaMensalLista rendas, Conexao conexao) throws SQLException {
		List<RendaMensal> renda = new RendaMensalDAO(conexao.getConnection()).lista();
		
		if (renda.size() > 0)
			rendas = new RendaMensalLista(renda);
		
		rendas = new RendaMensalLista();
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
	
	public static boolean validarRendaMensal(Line linha) {
		if (linha.quantityOfData() != Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_RECEITA)
			return false;
		
		if (linha.getData(Arquivo.COLUNA_TIPO) == null)
			return false;
		
		if (!new ValidacaoData().valida(linha.getData(Arquivo.COLUNA_DATA)))
			return false;
		
		if (converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_RECEITA)) == null)
			return false;
		
		return true;
	}
	
	public static boolean validarDespesa(Line linha) {
		if (!new ValidacaoData().valida(linha.getData(Arquivo.COLUNA_DATA_DESPESA)))
			return false;

		if (!new ValidacaoData().valida(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), ValidacaoData.REGEX_DATA_DIA_MES))
			return false;

		if (!new ValidacaoFormaPagamento().valida(linha.getData(Arquivo.COLUNA_FORMA_PAGAMENTO)))
			return false;

		if (linha.getData(Arquivo.COLUNA_DESCRICAO) == null || linha.getData(Arquivo.COLUNA_CATEGORIA) == null)
			return false;

		if (converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_DESPESA)) == null)
			return false;
		
		if (linha.quantityOfData() == Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_DESPESA)
			if (!linha.getData(Arquivo.COLUNA_SITUACAO).equalsIgnoreCase("Paga"))
				return false;
		
		return true;
	}
	
	public static boolean validarInvestimento(Line linha) {
		if (linha.quantityOfData() != Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO)
			return false;
		
		if (linha.getData(Arquivo.COLUNA_OBJETIVO) == null || linha.getData(Arquivo.COLUNA_ESTRATEGIA) == null)
			return false;
		
		if (linha.getData(Arquivo.COLUNA_NOME) == null)
			return false;
		
		if (converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_INVESTIDO)) == null || 
			converterNumeroReal(linha.getData(Arquivo.COLUNA_POSICAO)) == null)
			return false;

		if(converterNumeroReal(linha.getData(Arquivo.COLUNA_RENDIMENTO_BRUTO)) == null || 
		   converterNumeroReal(linha.getData(Arquivo.COLUNA_RENTABILIDADE)) == null)
			return false;
		
		if (!new ValidacaoData().valida(linha.getData(Arquivo.COLUNA_VENCIMENTO)))
			return false;
		
		return true;
	}
	
	public static Float converterNumeroReal(String numeroString) {
		try {
			return Float.parseFloat(numeroString.replace(".", "").replace(",", ".").replace("%", "").replace("RS ", ""));
		} catch (NullPointerException | NumberFormatException exception) {
			return null;
		}
	}
	
	public static Orcamento criarOrcamento(Line linha) {
		return new Orcamento(Short.parseShort(String.valueOf(MonthDay.parse(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), 
																			DateTimeFormatter.ofPattern(ValidacaoData.REGEX_DATA_DIA_MES)).getMonthValue())),
											   ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_DATA_DESPESA), ValidacaoData.REGEX_DATA_COMPLETA), 
											   ValidacaoData.formatarMesAno(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), ValidacaoData.REGEX_DATA_DIA_MES), 
											   converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_DESPESA)), criarDespesa(linha), criarFormaPagamento(linha), 
											   linha.quantityOfData() == Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_DESPESA ? true : false);
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

	public static Investimento criarInvestimento(Line linha) {
		return new Investimento(linha.getData(Arquivo.COLUNA_OBJETIVO), linha.getData(Arquivo.COLUNA_ESTRATEGIA), 
												  linha.getData(Arquivo.COLUNA_NOME), converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_INVESTIDO)) , 
												  converterNumeroReal(linha.getData(Arquivo.COLUNA_POSICAO)), 
												  converterNumeroReal(linha.getData(Arquivo.COLUNA_RENDIMENTO_BRUTO)), 
												  converterNumeroReal(linha.getData(Arquivo.COLUNA_RENTABILIDADE)), 
												  ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_VENCIMENTO), ValidacaoData.REGEX_DATA_COMPLETA));
	}

	public static RendaMensal criarRenda(Line linha) {
		return new RendaMensal(linha.getData(Arquivo.COLUNA_TIPO), 
												  ValidacaoData.formatarData(linha.getData(Arquivo.COLUNA_DATA), ValidacaoData.REGEX_DATA_COMPLETA), 
												  converterNumeroReal(linha.getData(Arquivo.COLUNA_VALOR_RECEITA)));
	}
}
