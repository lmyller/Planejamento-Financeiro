package lmv.planejamentofinanceiro;

import static lmv.planejamentofinanceiro.Arquivo.COLUNA_CATEGORIA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_DATA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_DATA_DESPESA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_DESCRICAO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_DIA_PAGAMENTO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_ESTRATEGIA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_FORMA_PAGAMENTO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_NOME;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_OBJETIVO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_POSICAO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_RENDIMENTO_BRUTO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_RENTABILIDADE;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_SITUACAO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_TIPO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_VALOR_DESPESA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_VALOR_INVESTIDO;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_VALOR_RECEITA;
import static lmv.planejamentofinanceiro.Arquivo.COLUNA_VENCIMENTO;
import static lmv.planejamentofinanceiro.Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_DESPESA;
import static lmv.planejamentofinanceiro.Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO;
import static lmv.planejamentofinanceiro.Arquivo.QUANTIDADE_COLUNAS_ARQUIVO_RECEITA;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.List;

import javax.swing.UIManager;

import lmv.planejamentofinanceiro.bd.conexao.Conexao;
import lmv.planejamentofinanceiro.dao.InvestimentoDAO;
import lmv.planejamentofinanceiro.dao.OrcamentoDAO;
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
import lmv.planejamentofinanceiro.modelo.Renda;
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
		OrcamentoLista orcamentoLista = null;
		InvestimentoLista investimentoLista = null;
		RendaMensalLista rendasLista = null;
		
		try {
			conexao = new Conexao(URL_BD, USUARIO_BD, SENHA_BD);
			
			rendasLista = obterRendasBD(conexao);
			orcamentoLista = obterOcamentosBD(conexao);
			investimentoLista = obterInvestimentosBD(conexao);
			
			new IgPlanejamentoFinanceiro(orcamentoLista, investimentoLista, rendasLista, conexao);
		} catch (SQLException e) {
			InputOutput.showError(ERRO_CONEXAO_BANCO_DE_DADOS, TITULO);;
		}
	}

	private RendaMensalLista obterRendasBD(Conexao conexao) throws SQLException {
		List<RendaMensal> renda = new RendaMensalDAO(conexao.getConnection()).lista();
		
		if (renda.size() > 0)
			return new RendaMensalLista(renda);
		
		return new RendaMensalLista();
	}
	
	private OrcamentoLista obterOcamentosBD(Conexao conexao) throws SQLException {
		List<Orcamento> orcamentos = new OrcamentoDAO(conexao.getConnection()).lista();
		
		if (orcamentos.size() > 0)
			return new OrcamentoLista(orcamentos);
		
		return new OrcamentoLista();
	}

	private InvestimentoLista obterInvestimentosBD(Conexao conexao) throws SQLException {
		List<Investimento> investimentos = new InvestimentoDAO(conexao.getConnection()).lista();
		
		if (investimentos.size() > 0)
			return new InvestimentoLista(investimentos);
		
		return new InvestimentoLista();
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
		if (linha.quantityOfData() != QUANTIDADE_COLUNAS_ARQUIVO_RECEITA)
			return false;
		
		if (linha.getData(COLUNA_TIPO) == null)
			return false;
		
		if (!new ValidacaoData().valida(linha.getData(COLUNA_DATA)))
			return false;
		
		if (converterNumeroReal(linha.getData(COLUNA_VALOR_RECEITA)) == null)
			return false;
		
		return true;
	}
	
	public static boolean validarDespesa(Line linha) {
		if (!new ValidacaoData().valida(linha.getData(COLUNA_DATA_DESPESA)))
			return false;

		if (!new ValidacaoData().valida(linha.getData(COLUNA_DIA_PAGAMENTO), ValidacaoData.DATA_DIA_MES))
			return false;

		if (!new ValidacaoFormaPagamento().valida(linha.getData(COLUNA_FORMA_PAGAMENTO)))
			return false;

		if (linha.getData(COLUNA_DESCRICAO) == null || linha.getData(COLUNA_CATEGORIA) == null)
			return false;

		if (converterNumeroReal(linha.getData(COLUNA_VALOR_DESPESA)) == null)
			return false;
		
		if (linha.quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_DESPESA)
			if (!linha.getData(COLUNA_SITUACAO).equalsIgnoreCase("Paga"))
				return false;
		
		return true;
	}
	
	public static boolean validarInvestimento(Line linha) {
		if (linha.quantityOfData() != QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO)
			return false;
		
		if (linha.getData(COLUNA_OBJETIVO) == null || linha.getData(COLUNA_ESTRATEGIA) == null)
			return false;
		
		if (linha.getData(COLUNA_NOME) == null)
			return false;
		
		if (converterNumeroReal(linha.getData(COLUNA_VALOR_INVESTIDO)) == null || 
			converterNumeroReal(linha.getData(COLUNA_POSICAO)) == null)
			return false;

		if(converterNumeroReal(linha.getData(COLUNA_RENDIMENTO_BRUTO)) == null || 
		   converterNumeroReal(linha.getData(COLUNA_RENTABILIDADE)) == null)
			return false;
		
		if (!new ValidacaoData().valida(linha.getData(COLUNA_VENCIMENTO)))
			return false;
		
		return true;
	}
	
	public static Double converterNumeroReal(String numeroString) {
		try {
			return Double.parseDouble(numeroString.replace(".", "").replace(",", ".").replace("%", "").replace("RS ", ""));
		} catch (NullPointerException | NumberFormatException exception) {
			return null;
		}
	}
	
	public static Orcamento criarOrcamento(short mesAno, LocalDate dataDespesa, MonthDay dataPagamento, Double valorDespesa,
																	    Despesa despesa, FormaPagamento formaPagamento, boolean situacao) {
		return new Orcamento(mesAno, despesa, dataDespesa, dataPagamento, formaPagamento, valorDespesa, situacao);
	}

	public static FormaPagamento criarFormaPagamento(String descricao) {
		return new FormaPagamento(descricao);
	}

	public static Despesa criarDespesa(String descricao, Categoria categoria) {
		return new Despesa(descricao, categoria);
	}

	public static Categoria criarCategoria(String descricao) {
		return new Categoria(descricao);
	}

	public static Investimento criarInvestimento(String objetivo, String estrategia, String nome, Double valorInvestido, Double posicao,
																			   Double rendimentoBruto, Double rentabilidade, LocalDate vencimento) {
		return new Investimento(objetivo, estrategia, nome, valorInvestido, posicao, rendimentoBruto, rentabilidade, vencimento);
	}

	public static RendaMensal criarRendaMensal(String descricao, LocalDate data, Double valor) {
		return new RendaMensal(descricao, data, valor);
	}
	
	public static Renda criarRenda(Integer codigo, String descricao) {
		Renda renda = new RendaMensal(descricao);
		renda.setCodigo(codigo);
		
		return renda;
	}
}
