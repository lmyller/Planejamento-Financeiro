package lmv.planejamentofinanceiro;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;
import lmv.planejamentofinanceiro.lista.RendaMensalLista;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import lmv.planejamentofinanceiro.validacao.ValidacaoFormaPagamento;
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
		File[] files = lerNomeArquivo(componente); 
		
		List<String> nomeArquivo = new ArrayList<>();
		
		try {
			for (var file : files)
				nomeArquivo.add(file.getCanonicalPath());
			
			lerArquivos(nomeArquivo, investimentoLista, orcamentoLista, rendaMensalLista);
		} catch (IOException iOException) {
			throw new IOException();
		} catch (NullPointerException nullPointerException) {
			throw new NullPointerException();
		}
	}

	private static void lerArquivos(List<String> nomeArquivos, InvestimentoLista investimentoLista, OrcamentoLista orcamentoLista, RendaMensalLista rendaMensalLista) {
		for (var nomeArquivo : nomeArquivos) {
			List<Line> linha = Reader.read(nomeArquivo, Reader.SEMICOLON);
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_DESPESA) {
				 relatorioArquivo(lerArquivoDespesa(linha, orcamentoLista), linha.size() - 1, nomeArquivo, PlanejamentoFinanceiro.TITULO);
			}
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO) {
				 relatorioArquivo(lerArquivoInvestimento(linha, investimentoLista), linha.size() - 1, nomeArquivo, PlanejamentoFinanceiro.TITULO);
			}
			
			if (linha.get(0).quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_RECEITA) {
				relatorioArquivo(lerArquivoRenda(linha, rendaMensalLista), linha.size() - 1, nomeArquivo, PlanejamentoFinanceiro.TITULO);
			}
		}
	}

	private static int lerArquivoRenda(List<Line> linhaArquivo, RendaMensalLista rendaMensalLista) {
		Float valor = null;
		int numeroLinhas = 0;
		
		for (var linha : linhaArquivo) {
			if (linha.quantityOfData() != QUANTIDADE_COLUNAS_ARQUIVO_RECEITA)
				continue;
			
			if (linha.getData(COLUNA_TIPO) == null)
				continue;
			
			if (!new ValidacaoData().valida(linha.getData(COLUNA_DATA)))
				continue;
			
			valor = converterNumeroReal(linha.getData(COLUNA_VALOR_RECEITA));
			
			if (valor == null)
				continue;
			
			numeroLinhas++;
			
			rendaMensalLista.inserir(PlanejamentoFinanceiro.criarRenda(linha, valor));
		}
		return numeroLinhas;
	}

	private static int lerArquivoInvestimento(List<Line> linhaArquivo, InvestimentoLista investimentoLista) {
		Float valorInvestido = null,
				 posicao = null,
				 rendimentoBruto = null,
				 rentabilidade = null;
		int numeroLinhas = 0;
		
		for (var linha : linhaArquivo) {
			if (linha.quantityOfData() != QUANTIDADE_COLUNAS_ARQUIVO_INVESTIMENTO)
				continue;
			
			if (linha.getData(COLUNA_OBJETIVO) == null || linha.getData(COLUNA_ESTRATEGIA) == null)
				continue;
			
			if (linha.getData(COLUNA_NOME) == null)
				continue;
			
			valorInvestido = converterNumeroReal(linha.getData(COLUNA_VALOR_INVESTIDO));
			posicao = converterNumeroReal(linha.getData(COLUNA_POSICAO));
			if (valorInvestido == null || posicao == null)
				continue;
				
			rendimentoBruto = converterNumeroReal(linha.getData(COLUNA_RENDIMENTO_BRUTO));
			rentabilidade = converterNumeroReal(linha.getData(COLUNA_RENTABILIDADE));
			if(rendimentoBruto == null || rentabilidade == null)
				continue;
			
			if (!new ValidacaoData().valida(linha.getData(COLUNA_VENCIMENTO)))
				continue;
			
			numeroLinhas++;
			
			investimentoLista.inserir(PlanejamentoFinanceiro.criarInvestimento(linha, valorInvestido, posicao, rendimentoBruto, rentabilidade));
		}
		
		return numeroLinhas;
	}

	private static int lerArquivoDespesa(List<Line> linhaArquivo, OrcamentoLista orcamentoLista) {
		int numeroLinhas = 0;
		Float valor = null;
		
		for (var linha : linhaArquivo) {
			if (!new ValidacaoData().valida(linha.getData(COLUNA_DATA_DESPESA)))
				continue;

			if (!new ValidacaoData().valida(linha.getData(COLUNA_DIA_PAGAMENTO), ValidacaoData.REGEX_DATA_DIA_MES))
				continue;

			if (!new ValidacaoFormaPagamento().valida(linha.getData(COLUNA_FORMA_PAGAMENTO)))
				continue;

			if (linha.getData(COLUNA_DESCRICAO) == null || linha.getData(COLUNA_CATEGORIA) == null)
				continue;

			valor = converterNumeroReal(linha.getData(COLUNA_VALOR_DESPESA));
			if (valor == null)
				continue;
			
			if (linha.quantityOfData() == QUANTIDADE_COLUNAS_ARQUIVO_DESPESA)
				if (!linha.getData(COLUNA_SITUACAO).equalsIgnoreCase("Paga"))
					continue;
			
			numeroLinhas++;
			
			orcamentoLista.inserir(PlanejamentoFinanceiro.criarOrcamento(linha, valor));
		}
		return numeroLinhas;
	}

	public static Float converterNumeroReal(String numeroString) {
		try {
			return Float.parseFloat(numeroString.replace(".", "").replace(",", ".").replace("%", "").replace("RS ", ""));
		} catch (NullPointerException | NumberFormatException exception) {
			return null;
		}
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
}
