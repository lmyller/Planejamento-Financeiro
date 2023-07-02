package lmv.planejamentofinanceiro;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.TITULO;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.converterNumeroReal;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarCategoria;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarDespesa;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarFormaPagamento;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarInvestimento;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarOrcamento;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarRendaMensal;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.validarDespesa;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.validarInvestimento;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.validarRendaMensal;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;
import lmv.planejamentofinanceiro.lista.RendaMensalLista;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import mos.es.InputOutput;
import mos.reader.Line;
import mos.reader.Reader;

public class Arquivo {
	
	private static final String RELATORIO_ARQUIVO = "Arquivo: %s\n%d linhas lidas com sucesso e %d erros"; 
	
	public static final short QUANTIDADE_COLUNAS_ARQUIVO_RECEITA = 3,
											 QUANTIDADE_COLUNAS_ARQUIVO_DESPESA = 7,
											 QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO = 8,
											 COLUNA_TIPO = 0,
										     COLUNA_DATA = 1,
										     COLUNA_VALOR_RECEITA = 2,
										     COLUNA_DATA_DESPESA = 0,
										     COLUNA_DIA_PAGAMENTO = 1,
										     COLUNA_FORMA_PAGAMENTO = 2,
										     COLUNA_DESCRICAO = 3,
										     COLUNA_CATEGORIA = 4,
										     COLUNA_VALOR_DESPESA = 5,
										     COLUNA_SITUACAO = 6,
										     COLUNA_OBJETIVO = 0,
										     COLUNA_ESTRATEGIA = 1,
										     COLUNA_NOME = 2,
										     COLUNA_VALOR_INVESTIDO = 3,
										     COLUNA_POSICAO = 4,
										     COLUNA_RENDIMENTO_BRUTO = 5,
										     COLUNA_RENTABILIDADE = 6,
										     COLUNA_VENCIMENTO = 7;
								   
	
	public static void importarArquivos(Component componente, InvestimentoLista investimentoLista, OrcamentoLista orcamentoLista, 
																RendaMensalLista rendaMensalLista) throws IOException, NullPointerException {
		List<DadosArquivo> dadosArquivos = new ArrayList<>();
		File[] files = lerNomeArquivo(componente); 
				
		for (var file : files)
			dadosArquivos.add(new DadosArquivo(file.getCanonicalPath(), file.getName()));
		
		lerArquivos(files, dadosArquivos, investimentoLista, orcamentoLista, rendaMensalLista);
	}

	private static void lerArquivos(File[] files, List<DadosArquivo> dadosArquivos, InvestimentoLista investimentoLista, OrcamentoLista orcamentoLista, 
													   RendaMensalLista rendaMensalLista) {
		for (var dadosArquivo : dadosArquivos) {
			List<Line> linha = Reader.read(dadosArquivo.getCaminhoArquivo(), Reader.SEMICOLON);
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_DESPESA) {
				 relatorioArquivo(lerArquivoDespesa(linha, orcamentoLista), linha.size() - 1, dadosArquivo.getNomeArquivo(), TITULO);
			}
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO) {
				 relatorioArquivo(lerArquivoInvestimento(linha, investimentoLista), linha.size() - 1, dadosArquivo.getNomeArquivo(), TITULO);
			}
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_RECEITA) {
				relatorioArquivo(lerArquivoRenda(linha, rendaMensalLista), linha.size() - 1, dadosArquivo.getNomeArquivo(), TITULO);
			}
		}
	}

	private static int lerArquivoRenda(List<Line> linhaArquivo, RendaMensalLista rendaMensalLista) {
		int numeroLinhas = 0;
		
		for (var linha : linhaArquivo) {
			if (validarRendaMensal(linha)) {
				rendaMensalLista.inserir(criarRendaMensal(linha.getData(COLUNA_TIPO), 
														  ValidacaoData.formatarData(linha.getData(COLUNA_DATA), ValidacaoData.DATA_COMPLETA),
														  converterNumeroReal(linha.getData(COLUNA_VALOR_RECEITA))));
				
				numeroLinhas++;
			}
		}
		return numeroLinhas;
	}

	private static int lerArquivoInvestimento(List<Line> linhaArquivo, InvestimentoLista investimentoLista) {
		int numeroLinhas = 0;
		
		for (var linha : linhaArquivo) {
			if (validarInvestimento(linha)) {
				investimentoLista.inserir(criarInvestimento(linha.getData(COLUNA_OBJETIVO), linha.getData(COLUNA_ESTRATEGIA), 
						  								  linha.getData(COLUNA_NOME), converterNumeroReal(linha.getData(COLUNA_VALOR_INVESTIDO)) , 
						  								  converterNumeroReal(linha.getData(COLUNA_POSICAO)), 
						  								  converterNumeroReal(linha.getData(COLUNA_RENDIMENTO_BRUTO)), 
						  								  converterNumeroReal(linha.getData(COLUNA_RENTABILIDADE)), 
						  								  ValidacaoData.formatarData(linha.getData(COLUNA_VENCIMENTO), ValidacaoData.DATA_COMPLETA)));
				
				numeroLinhas++;
			}
		}
		return numeroLinhas;
	}

	private static int lerArquivoDespesa(List<Line> linhaArquivo, OrcamentoLista orcamentoLista) {
		int numeroLinhas = 0;
		
		for (var linha : linhaArquivo) {
			if (validarDespesa(linha)) {
				orcamentoLista.inserir(criarOrcamento(Short.parseShort(String.valueOf(MonthDay.parse(linha.getData(Arquivo.COLUNA_DIA_PAGAMENTO), 
													   DateTimeFormatter.ofPattern(ValidacaoData.DATA_DIA_MES)).getMonthValue())),
													   ValidacaoData.formatarData(linha.getData(COLUNA_DATA_DESPESA), ValidacaoData.DATA_COMPLETA), 
													   ValidacaoData.formatarMesAno(linha.getData(COLUNA_DIA_PAGAMENTO), ValidacaoData.DATA_DIA_MES), 
													   converterNumeroReal(linha.getData(COLUNA_VALOR_DESPESA)), 
													   criarDespesa(linha.getData(COLUNA_DESCRICAO), criarCategoria(linha.getData(COLUNA_CATEGORIA))), 
													   criarFormaPagamento(linha.getData(COLUNA_FORMA_PAGAMENTO)), 
													   linha.quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_DESPESA ? true : false));
				
				numeroLinhas++;
			}
		}
		return numeroLinhas;
	}

	private static File[] lerNomeArquivo(Component component) {
		JFileChooser jFileChooser = criarFileChooser();
		
		int opcao = jFileChooser.showOpenDialog(component);
		
		try {
			return opcao == JFileChooser.APPROVE_OPTION ? jFileChooser.getSelectedFiles() : null;
		} catch (Exception e) {
			return null;
		}
	}

	private static JFileChooser criarFileChooser() {
	final String DIRETORIO_ATUAL = ".",
					   ARQUIVO_CSV = "Arquivo CSV (*csv)",
					   EXTENSAO_CSV = "csv";
		
		JFileChooser jFileChooser = new JFileChooser();
		
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		jFileChooser.setCurrentDirectory(new File(DIRETORIO_ATUAL));
		
		jFileChooser.setMultiSelectionEnabled(true);
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(ARQUIVO_CSV, EXTENSAO_CSV);
		
		jFileChooser.setFileFilter(filter);
		
		return jFileChooser;
	}
	
	private static void relatorioArquivo(int lerArquivoDespesa, int quantidadeLinhas, String nomeArquivo, String titulo) {
		InputOutput.showInfo(String.format(RELATORIO_ARQUIVO, nomeArquivo, lerArquivoDespesa, quantidadeLinhas - lerArquivoDespesa
																     ), titulo);
	}
	
	private static class DadosArquivo{
		private String caminhoArquivo;
		private String nomeArquivo;
		
		public DadosArquivo(String caminhoArquivo, String nomeArquivo) {
			this.caminhoArquivo = caminhoArquivo;
			this.nomeArquivo = nomeArquivo;
		}

		public final String getCaminhoArquivo() {
			return caminhoArquivo;
		}

		public final String getNomeArquivo() {
			return nomeArquivo;
		}
	}
}
