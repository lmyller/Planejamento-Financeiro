package lmv.planejamentofinanceiro.gui;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.BR;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.ERRO_SALVAR_BD;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.PT;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.TITULO;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarRenda;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import lmv.planejamentofinanceiro.Arquivo;
import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.bd.conexao.Conexao;
import lmv.planejamentofinanceiro.dao.CategoriaDAO;
import lmv.planejamentofinanceiro.dao.DespesaDAO;
import lmv.planejamentofinanceiro.dao.FormaPagamentoDAO;
import lmv.planejamentofinanceiro.dao.InvestimentoDAO;
import lmv.planejamentofinanceiro.dao.OrcamentoDAO;
import lmv.planejamentofinanceiro.dao.RendaDAO;
import lmv.planejamentofinanceiro.dao.RendaMensalDAO;
import lmv.planejamentofinanceiro.enumeracao.Mes;
import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;
import lmv.planejamentofinanceiro.lista.RendaMensalLista;
import lmv.planejamentofinanceiro.modelo.Categoria;
import lmv.planejamentofinanceiro.modelo.Despesa;
import lmv.planejamentofinanceiro.modelo.FormaPagamento;
import lmv.planejamentofinanceiro.modelo.Renda;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import mos.es.InputOutput;
import net.miginfocom.swing.MigLayout;

public class IgPlanejamentoFinanceiro extends JFrame {
	
	private final String ERRO_ABRIR_ARQUIVO = "Erro ao abrir arquivo",
									ARQUIVO_NAO_SELECIONADO = "Arquivo não selecionado";
	
	public static final int COLUNA_DATA = 0,
									   COLUNA_DIA = 1,
									   COLUNA_TIPO = 2,
									   COLUNA_DESCRICAO = 3,
									   COLUNA_VALOR = 4,
									   COLUNA_PAGO = 5;
	
	private IgPlanejamentoFinanceiro igPlanejamentoFinanceiro;
	private JPanel contentPane;
	private JTextField receitaTextField;
	private JTextField despesaTextField;
	private JTextField saldoTextField;
	private JTextField totalPagoTextField;
	private JTextField totalPagarTextField;
	private JTextField investimentoTextField;
	private JPanel orcamentoPanel;
	private JLabel mesLabel;
	private JTable orcamentoTable;
	DefaultTableModel defaultTableModelOrcamento;
	private JButton pesquisarDespesaButton;
	private JButton graficoBarrasButton;
	private JButton graficoPizzaButton;
	private JButton importarButton;
	private JButton investimentosButton;
	private JComboBox<Object> mesComboBox;
	private JLabel categoriaLabel;
	private JComboBox<Object> categoriaComboBox;
	private JScrollPane orcamentoScrollPane;

	/**
	 * 
	 * @param orcamentoLista
	 * @param investimentoLista
	 * @param rendaMensalLista
	 * @param conexao
	 */
	public IgPlanejamentoFinanceiro(OrcamentoLista orcamentoLista, InvestimentoLista investimentoLista, RendaMensalLista rendaMensalLista, Conexao conexao) {
		this.igPlanejamentoFinanceiro = this;
		String[] colunas = new String[]{ "Data", "Dia", "Tipo", "Descri\u00E7\u00E3o", "Valor", "Paga"};
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					salvarBD(orcamentoLista, investimentoLista, rendaMensalLista, conexao);
				} catch (SQLException sQLException) {
					InputOutput.showError(ERRO_SALVAR_BD, TITULO);
				} finally {
					System.exit(0);
				}
			}
		});
		
		setResizable(false);
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setTitle("Planejamento Financeiro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1122, 490);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[132px][4px][132px][4px][132px][140px][132px][4px][132px][66px][206px]", "[19px][17px][343px][38px]"));
		
		JLabel receitaLabel = new JLabel("Receitas");
		receitaLabel.setForeground(new Color(0, 0, 255));
		receitaLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(receitaLabel, "cell 0 0,alignx center,growy");
		
		JLabel despesasLabel = new JLabel("Despesas");
		despesasLabel.setForeground(new Color(0, 0, 255));
		despesasLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(despesasLabel, "cell 2 0,alignx center,aligny center");
		
		JLabel saldoLabel = new JLabel("Saldo");
		saldoLabel.setForeground(new Color(0, 0, 255));
		saldoLabel.setBackground(new Color(0, 0, 0));
		saldoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(saldoLabel, "cell 4 0,alignx center,aligny center");
		
		receitaTextField = new JTextField();
		receitaTextField.setHorizontalAlignment(SwingConstants.CENTER);
		receitaTextField.setBorder(null);
		receitaTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		receitaTextField.setText("R$ 0");
		receitaTextField.setEditable(false);
		contentPane.add(receitaTextField, "cell 0 1,growx,aligny top");
		receitaTextField.setColumns(10);
		
		despesaTextField = new JTextField();
		despesaTextField.setText("R$ 0");
		despesaTextField.setHorizontalAlignment(SwingConstants.CENTER);
		despesaTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		despesaTextField.setEditable(false);
		despesaTextField.setBorder(null);
		contentPane.add(despesaTextField, "cell 2 1,growx,aligny top");
		despesaTextField.setColumns(10);
		
		JLabel totalPagoLabel = new JLabel("Total Pago");
		totalPagoLabel.setForeground(new Color(0, 0, 255));
		totalPagoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(totalPagoLabel, "cell 6 0,alignx center,aligny center");
		
		saldoTextField = new JTextField();
		saldoTextField.setBorder(null);
		saldoTextField.setEditable(false);
		saldoTextField.setText("R$ 0");
		saldoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		saldoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(saldoTextField, "cell 4 1,growx,aligny top");
		saldoTextField.setColumns(10);
		
		totalPagoTextField = new JTextField();
		totalPagoTextField.setBorder(null);
		totalPagoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalPagoTextField.setText("R$ 0");
		totalPagoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalPagoTextField.setEditable(false);
		contentPane.add(totalPagoTextField, "cell 6 1,growx,aligny top");
		totalPagoTextField.setColumns(10);
		
		JLabel totalPagarLabel = new JLabel("Total A Pagar");
		totalPagarLabel.setForeground(new Color(0, 0, 255));
		totalPagarLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(totalPagarLabel, "cell 8 0,alignx center,aligny center");
		
		totalPagarTextField = new JTextField();
		totalPagarTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalPagarTextField.setText("R$ 0");
		totalPagarTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalPagarTextField.setEditable(false);
		totalPagarTextField.setBorder(null);
		contentPane.add(totalPagarTextField, "cell 8 1,growx,aligny bottom");
		totalPagarTextField.setColumns(10);
		
		JLabel investimentoLabel = new JLabel("Investimentos");
		investimentoLabel.setForeground(new Color(0, 0, 255));
		investimentoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(investimentoLabel, "cell 10 0,alignx center,aligny center");
		
		investimentoTextField = new JTextField();
		investimentoTextField.setEditable(false);
		investimentoTextField.setBorder(null);
		investimentoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		investimentoTextField.setText("R$ 0");
		investimentoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(investimentoTextField, "cell 10 1,growx,aligny top");
		investimentoTextField.setColumns(10);
		
		orcamentoPanel = new JPanel();
		orcamentoPanel.setBorder(new TitledBorder(null, "Or\u00E7amento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		orcamentoPanel.setBackground(new Color(255, 255, 255));
		contentPane.add(orcamentoPanel, "cell 0 2 11 1,grow");
		orcamentoPanel.setLayout(null);
		
		mesLabel = new JLabel("Mês:");
		mesLabel.setBounds(21, 30, 25, 16);
		mesLabel.setDisplayedMnemonic(KeyEvent.VK_M);
		orcamentoPanel.add(mesLabel);
		
		orcamentoScrollPane = new JScrollPane();
		orcamentoScrollPane.setBounds(21, 59, 544, 224);
		orcamentoScrollPane.setBackground(new Color(255, 255, 255));
		orcamentoScrollPane.getViewport().setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(orcamentoScrollPane);
		
		orcamentoTable = new JTable();
		orcamentoTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				alterarTabela();
			}
		});
		orcamentoTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int keyEvent = e.getKeyCode();
				System.out.println(KeyEvent.getKeyText(e.getKeyCode()));
				orcamentoTable.requestFocus();
				if (keyEvent == KeyEvent.VK_RIGHT || keyEvent == KeyEvent.VK_LEFT
					|| keyEvent == KeyEvent.VK_ENTER) {
					
					alterarTabela();
				}
			}
		});
		orcamentoScrollPane.setViewportView(orcamentoTable);
		orcamentoTable.getTableHeader().setBackground(new Color(255, 255, 255));
		orcamentoTable.setBackground(new Color(255, 255, 255));
		orcamentoTable.setShowVerticalLines(true);
		orcamentoTable.setShowHorizontalLines(true);
		orcamentoTable.setCellSelectionEnabled(true);
		defaultTableModelOrcamento = new DefaultTableModel(colunas, 1);
		orcamentoTable.setModel(defaultTableModelOrcamento);
		orcamentoTable.getColumnModel().getColumn(COLUNA_PAGO).setCellEditor(orcamentoTable.getDefaultEditor(Boolean.class));
		orcamentoTable.getColumnModel().getColumn(COLUNA_PAGO).setCellRenderer(orcamentoTable.getDefaultRenderer(Boolean.class));
		orcamentoTable.getColumnModel().getColumn(0).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(0).setPreferredWidth(55);
		orcamentoTable.getColumnModel().getColumn(1).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(1).setPreferredWidth(15);
		orcamentoTable.getColumnModel().getColumn(2).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(2).setPreferredWidth(40);
		orcamentoTable.getColumnModel().getColumn(3).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(3).setPreferredWidth(250);
		orcamentoTable.getColumnModel().getColumn(4).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		orcamentoTable.getColumnModel().getColumn(5).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(5).setPreferredWidth(25);
		
		pesquisarDespesaButton = new JButton("Pesquisar Despesa...");
		pesquisarDespesaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgPesquisarDespesa(igPlanejamentoFinanceiro, orcamentoLista);
			}
		});
		pesquisarDespesaButton.setBounds(21, 295, 141, 28);
		pesquisarDespesaButton.setMnemonic(KeyEvent.VK_P);
		pesquisarDespesaButton.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(pesquisarDespesaButton);
		
		graficoBarrasButton = new JButton("Gráfico em Barras");
		graficoBarrasButton.setBounds(174, 295, 124, 28);
		graficoBarrasButton.setMnemonic(KeyEvent.VK_B);
		graficoBarrasButton.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(graficoBarrasButton);
		
		graficoPizzaButton = new JButton("Gráfico em Pizza");
		graficoPizzaButton.setBounds(310, 295, 118, 28);
		graficoPizzaButton.setMnemonic(KeyEvent.VK_G);
		graficoPizzaButton.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(graficoPizzaButton);
		
		mesComboBox = new JComboBox<>();
		mesComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				preencherTabela(orcamentoLista, mesComboBox.getSelectedIndex() + 1, categoriaComboBox.getSelectedItem().toString());
			}
		});
		mesComboBox.setBounds(50, 25, 101, 26);
		mesLabel.setLabelFor(mesComboBox);
		mesComboBox.setModel(new DefaultComboBoxModel<Object>(Mes.values()));
		mesComboBox.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(mesComboBox);
		
		categoriaLabel = new JLabel("Categoria:");
		categoriaLabel.setBounds(155, 30, 54, 16);
		categoriaLabel.setDisplayedMnemonic(KeyEvent.VK_C);
		orcamentoPanel.add(categoriaLabel);
		
		categoriaComboBox = new JComboBox<>();
		categoriaComboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				preencherTabela(orcamentoLista, mesComboBox.getSelectedIndex() + 1, categoriaComboBox.getSelectedItem().toString());
			}
		});
		categoriaComboBox.setBounds(213, 25, 119, 26);
		categoriaLabel.setLabelFor(categoriaComboBox);
		categoriaComboBox.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(categoriaComboBox);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel, "cell 10 3,alignx right,growy");
		
		importarButton = new JButton("Importar...");
		importarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Arquivo.importarArquivos(igPlanejamentoFinanceiro, investimentoLista, orcamentoLista, rendaMensalLista);
				} catch (IOException iOExcepion) {
					InputOutput.showError(ERRO_ABRIR_ARQUIVO, TITULO);
				} catch (NullPointerException nullPointerException) {
					InputOutput.showError(ARQUIVO_NAO_SELECIONADO, TITULO);
				}
				if (orcamentoLista.tamanhoLista() > 0) {
					preencherTabela(orcamentoLista, mesComboBox.getSelectedIndex() + 1, orcamentoLista.obter(0).getDespesa().getCategoria().getDescricao());
					if (orcamentoLista.tamanhoLista() > 0)
						adicionarCategorias(orcamentoLista);
				}
				
				totalPago(orcamentoLista);
				totalNaoPago(orcamentoLista);
				despesaTotal(orcamentoLista);
				totalAcumulado(investimentoLista);
				receitaTotal(rendaMensalLista);
				saldo(rendaMensalLista, orcamentoLista);
			}
		});
		panel.add(importarButton);
		importarButton.setMnemonic(KeyEvent.VK_O);
		importarButton.setBackground(new Color(255, 255, 255));
		
		investimentosButton = new JButton("Investimentos...");
		investimentosButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new IgInvestimento(igPlanejamentoFinanceiro, investimentoLista);
			}
		});
		panel.add(investimentosButton);
		investimentosButton.setMnemonic(KeyEvent.VK_I);
		investimentosButton.setBackground(new Color(255, 255, 255));
		
		if (rendaMensalLista.tamanhoLista() > 0) {
			receitaTotal(rendaMensalLista);
		}
		
		if (orcamentoLista.tamanhoLista() > 0) {
			totalPago(orcamentoLista);
			totalNaoPago(orcamentoLista);
			despesaTotal(orcamentoLista);
			preencherTabela(orcamentoLista, mesComboBox.getSelectedIndex() + 1, orcamentoLista.obter(0).getDespesa().getCategoria().getDescricao());
		}
		
		if (rendaMensalLista.tamanhoLista() > 0 && orcamentoLista.tamanhoLista() > 0)
			saldo(rendaMensalLista, orcamentoLista);
		
		if (investimentoLista.tamanhoLista() > 0)
			totalAcumulado(investimentoLista);
		
		setVisible(true);
	}
	
	public JTable getOrcamentoTable() {
		return orcamentoTable;
	}
	
	private void alterarTabela() {
		int linha = orcamentoTable.getSelectedRow();
		int coluna = orcamentoTable.getSelectedColumn();
		Object celulaTabela = orcamentoTable.getValueAt(linha, coluna); 
		
		if (celulaTabela != null)
			if (!celulaTabela.toString().isBlank())
				if (coluna == COLUNA_DATA && !new ValidacaoData().valida(celulaTabela.toString())) {
					InputOutput.showError(ValidacaoData.DATA_INVALIDA, TITULO);
					orcamentoTable.setValueAt(null, linha, coluna);
				}
}
	
	private void preencherTabela(OrcamentoLista orcamentoLista, int mes, String categoria) {
		if (defaultTableModelOrcamento.getRowCount() > 0)
			apagarTabela();
		
		for (var orcamento : orcamentoLista) {
			int indice = -1;
			
			if (orcamento.getDataPagamento().getMonthValue() == mes && orcamento.getDespesa().getCategoria().getDescricao().equalsIgnoreCase(categoria)) {
				indice++;
				
				Object[] dadosOrcamento = orcamento.toList();
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[0], indice, 0);
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[1], indice, 1);
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[2], indice, 2);
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[3], indice, 3);
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[4], indice, 4);
				defaultTableModelOrcamento.setValueAt(dadosOrcamento[5], indice, 5);
				
				if (defaultTableModelOrcamento.getRowCount() == indice + 1)
					adicionarLinha();
			}
		}
	}
	
	private void adicionarLinha() {
		defaultTableModelOrcamento.addRow(new Object[] {null, null, null, null, null, null});
	}

	private void apagarTabela() {
		for (int indice = 0; indice < defaultTableModelOrcamento.getRowCount(); indice++) {
			defaultTableModelOrcamento.setValueAt(null, indice, 0);
			defaultTableModelOrcamento.setValueAt(null, indice, 1);
			defaultTableModelOrcamento.setValueAt(null, indice, 2);
			defaultTableModelOrcamento.setValueAt(null, indice, 3);
			defaultTableModelOrcamento.setValueAt(null, indice, 4);
			defaultTableModelOrcamento.setValueAt(null, indice, 5);
		}
	}

	private void adicionarCategorias(OrcamentoLista orcamentoLista) {
		for (var orcamento : orcamentoLista) {
			if (!categoriaExistente(orcamento.getDespesa().getCategoria().getDescricao()))
				categoriaComboBox.addItem(orcamento.getDespesa().getCategoria().getDescricao().toUpperCase());
		}
	}

	private boolean categoriaExistente(String descricao) {
		for (int indice = 0; indice < categoriaComboBox.getItemCount(); indice++)
			if (descricao.equalsIgnoreCase(categoriaComboBox.getItemAt(indice).toString()))
				return true;
		
		return false;
	}
	
	private void totalPago(OrcamentoLista orcamentoLista) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of(PT, PlanejamentoFinanceiro.BR));
		
		totalPagoTextField.setText(numberFormat.format(calculaTotalPago(orcamentoLista)));
	}

	private Double calculaTotalPago(OrcamentoLista orcamentoLista) {
		Double totalPago = 0D;
		
		for (var orcamento : orcamentoLista)
			if (orcamento.isSituacao())
				totalPago += orcamento.getValor();
		
		return totalPago;
	}
	
	private void totalNaoPago(OrcamentoLista orcamentoLista) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of(PT, BR));
		
		totalPagarTextField.setText(numberFormat.format(calculaTotalNaoPago(orcamentoLista)));
	}

	private Double calculaTotalNaoPago(OrcamentoLista orcamentoLista) {
		Double totalNaoPago = 0D;
		
		for (var orcamento : orcamentoLista)
			if (!orcamento.isSituacao())
				totalNaoPago += orcamento.getValor();
		
		return totalNaoPago;
	}
	
	private void despesaTotal(OrcamentoLista orcamentoLista) {
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.of(PT, BR));
		
		despesaTextField.setText(numberFormat.format(calculaTotalPago(orcamentoLista) + calculaTotalNaoPago(orcamentoLista)));
	}
	
	private void totalAcumulado(InvestimentoLista investimentoLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PT, BR));
		
		investimentoTextField.setText(formatPreco.format(calculaTotalAcumulado(investimentoLista)));
	}

	private Double calculaTotalAcumulado(InvestimentoLista investimentoLista) {
		Double totalAcumulado = 0D;
		
		for (var investimento : investimentoLista)
			totalAcumulado += investimento.getPosicao();
		
		return totalAcumulado;
	}
	
	private void receitaTotal(RendaMensalLista rendaMensalLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PT, BR));
		
		receitaTextField.setText(formatPreco.format(calculaReceitaTotal(rendaMensalLista)));
	}

	private Double calculaReceitaTotal(RendaMensalLista rendaMensalLista) {
		Double receitaTotal = 0D;
		
		for (var rendaMensal : rendaMensalLista)
			receitaTotal += rendaMensal.getValor();
		
		return receitaTotal;
	}
	
	private void saldo(RendaMensalLista rendaMensalLista, OrcamentoLista orcamentoLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PT, BR));
		
		saldoTextField.setText(formatPreco.format(calculaReceitaTotal(rendaMensalLista) - (calculaTotalPago(orcamentoLista) + calculaTotalNaoPago(orcamentoLista))));
	}
	
	private void salvarBD(OrcamentoLista orcamentoLista, InvestimentoLista investimentoLista, RendaMensalLista rendaMensalLista, Conexao conexao) throws SQLException{
		salvarRendaBD(rendaMensalLista, conexao);
		salvarOrcamentoBD(orcamentoLista, conexao);
		salvarInvestimentoBD(investimentoLista, conexao);
		conexao.close();
	}

	private void salvarRendaBD(RendaMensalLista rendaMensalLista, Conexao conexao) throws SQLException{
		for (var rendaMensal : rendaMensalLista) {
			RendaDAO rendaDAO = new RendaDAO(conexao.getConnection());
			RendaMensalDAO rendaMensalDAO = new RendaMensalDAO(conexao.getConnection());
			Renda renda = criarRenda(rendaMensal.getCodigo(), rendaMensal.getDescricao());
			
			if (rendaDAO.obter(renda) == null)
				rendaDAO.adicionar(renda);
			
			rendaMensal.setCodigo(rendaDAO.obterCodigo(renda));
			
			if (rendaMensalDAO.obter(rendaMensal) == null)
				rendaMensalDAO.adicionar(rendaMensal);
		}
	}

	private void salvarOrcamentoBD(OrcamentoLista orcamentoLista, Conexao conexao) throws SQLException {
		OrcamentoDAO orcamentoDAO = new OrcamentoDAO(conexao.getConnection());
		
		for (var orcamento : orcamentoLista) {
			salvarDespesaBD(orcamento.getDespesa(), conexao);
			salvarFormaPagamentoBD(orcamento.getFormaPagamento(), conexao);
			
			if (orcamentoDAO.obter(orcamento) == null)
				orcamentoDAO.adicionar(orcamento);
		}
	}

	private void salvarDespesaBD(Despesa despesa, Conexao conexao) throws SQLException {
		DespesaDAO despesaDAO = new DespesaDAO(conexao.getConnection());
		
		salvarCategoriaDB(despesa.getCategoria(), conexao);
		
		if (despesaDAO.obter(despesa) == null)
			despesaDAO.adicionar(despesa);
		
		despesa.setCodigo(despesaDAO.obterCodigo(despesa));
	}

	private void salvarCategoriaDB(Categoria categoria, Conexao conexao) throws SQLException {
		CategoriaDAO categoriaDAO = new CategoriaDAO(conexao.getConnection());
		
		if (categoriaDAO.obter(categoria) == null)
			categoriaDAO.adicionar(categoria);
		
		categoria.setCodigo(categoriaDAO.obterCodigo(categoria));
	}

	private void salvarFormaPagamentoBD(FormaPagamento formaPagamento, Conexao conexao) throws SQLException {
		FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO(conexao.getConnection());
		
		if (formaPagamentoDAO.obter(formaPagamento) == null)
			formaPagamentoDAO.adicionar(formaPagamento);
		
		formaPagamento.setCodigo(formaPagamentoDAO.obterCodigo(formaPagamento));
	}
	
	private void salvarInvestimentoBD(InvestimentoLista investimentoLista, Conexao conexao) throws SQLException {
		InvestimentoDAO investimentoDAO = new InvestimentoDAO(conexao.getConnection());
		
		for (var investimento : investimentoLista) {
			if (investimentoDAO.obter(investimento) == null)
				investimentoDAO.adicionar(investimento);
		}
	}
}