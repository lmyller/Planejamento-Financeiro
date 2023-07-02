package lmv.planejamentofinanceiro.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import net.miginfocom.swing.MigLayout;

public class IgInvestimento extends JDialog {

	DefaultTableModel defaultTableModelInvestimento;
	private JPanel contentPane;
	private JTextField rendimentoBrutoTextField;
	private JTextField totalAcumuladoTextField;
	private JTextField totalInvestidoTextField;
	private JPanel investimentosPanel;
	private JButton fecharButton;
	private JButton graficoColunasButton;
	private JButton graficoBarrasButton;
	private JScrollPane investimentoScrollPane;
	private JTable investimentoTable;

	/**
	 * Create the frame.
	 */
	public IgInvestimento(IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, InvestimentoLista investimentoLista) {
		String[] colunas = new String[] {
				"Objetivo", "Estrat\u00E9gia", "Nome", "Valor Investido", "Posi\u00E7\u00E3o", "Rendimento Bruto", "Rentabilidade", "Vencimento"
			};
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharJanela(igPlanejamentoFinanceiro);
			}
		});
		
		setBackground(new Color(255, 255, 255));
		setTitle("Planejamento Financeiro - Investimentos");
		setResizable(false);
		setBounds(100, 100, 1129, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[91px][4px][94px][4px][104px][465px][124px][4px][132px][4px][65px]", "[16px][16px][263px][28px]"));
		
		JLabel totalInvestidoLabel = new JLabel("Total Investido");
		totalInvestidoLabel.setForeground(new Color(0, 0, 255));
		totalInvestidoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		totalInvestidoLabel.setBackground(new Color(255, 255, 255));
		contentPane.add(totalInvestidoLabel, "cell 0 0,alignx center,aligny top");
		
		JLabel totalAcumuladoLabel = new JLabel("Total Acumulado");
		totalAcumuladoLabel.setBackground(new Color(255, 255, 255));
		totalAcumuladoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		totalAcumuladoLabel.setForeground(new Color(0, 0, 255));
		contentPane.add(totalAcumuladoLabel, "cell 2 0,alignx left,aligny top");
		
		JLabel rendimentoBrutoLabel = new JLabel("Rendimento Bruto");
		rendimentoBrutoLabel.setBackground(new Color(255, 255, 255));
		rendimentoBrutoLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		rendimentoBrutoLabel.setForeground(new Color(0, 0, 255));
		contentPane.add(rendimentoBrutoLabel, "cell 4 0,alignx left,aligny top");
		
		rendimentoBrutoTextField = new JTextField();
		rendimentoBrutoTextField.setEditable(false);
		rendimentoBrutoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		rendimentoBrutoTextField.setFont(new Font("SansSerif", Font.BOLD, 12));
		rendimentoBrutoTextField.setText("R$ 0");
		rendimentoBrutoTextField.setBorder(null);
		contentPane.add(rendimentoBrutoTextField, "cell 4 1,alignx left,aligny top");
		rendimentoBrutoTextField.setColumns(10);
		
		totalAcumuladoTextField = new JTextField();
		totalAcumuladoTextField.setEditable(false);
		totalAcumuladoTextField.setBorder(null);
		totalAcumuladoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalAcumuladoTextField.setText("R$ 0");
		totalAcumuladoTextField.setFont(new Font("SansSerif", Font.BOLD, 12));
		contentPane.add(totalAcumuladoTextField, "cell 2 1,growx,aligny top");
		totalAcumuladoTextField.setColumns(10);
		
		totalInvestidoTextField = new JTextField();
		totalInvestidoTextField.setEditable(false);
		totalInvestidoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalInvestidoTextField.setText("R$ 0");
		totalInvestidoTextField.setFont(new Font("SansSerif", Font.BOLD, 12));
		totalInvestidoTextField.setBorder(null);
		contentPane.add(totalInvestidoTextField, "cell 0 1,growx,aligny top");
		totalInvestidoTextField.setColumns(10);
		
		investimentosPanel = new JPanel();
		investimentosPanel.setBackground(new Color(255, 255, 255));
		investimentosPanel.setBorder(new TitledBorder(null, "Investimentos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(investimentosPanel, "cell 0 2 11 1,grow");
		investimentosPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		investimentoScrollPane = new JScrollPane();
		investimentoScrollPane.setBackground(new Color(255, 255, 255));
		investimentoScrollPane.getViewport().setBackground(new Color(255, 255, 255));
		investimentosPanel.add(investimentoScrollPane, "cell 0 0,grow");
		
		investimentoTable = new JTable();
		investimentoTable.setShowVerticalLines(true);
		investimentoTable.setShowHorizontalLines(true);
		defaultTableModelInvestimento = new DefaultTableModel(colunas, 1);
		investimentoTable.setModel(defaultTableModelInvestimento);
		investimentoTable.getColumnModel().getColumn(0).setResizable(false);
		investimentoTable.getColumnModel().getColumn(1).setResizable(false);
		investimentoTable.getColumnModel().getColumn(2).setResizable(false);
		investimentoTable.getColumnModel().getColumn(3).setResizable(false);
		investimentoTable.getColumnModel().getColumn(4).setResizable(false);
		investimentoTable.getColumnModel().getColumn(5).setResizable(false);
		investimentoTable.getColumnModel().getColumn(6).setResizable(false);
		investimentoTable.getColumnModel().getColumn(7).setResizable(false);
		investimentoScrollPane.setViewportView(investimentoTable);
		
		graficoBarrasButton = new JButton("Gráfico em Barras");
		graficoBarrasButton.setMnemonic(KeyEvent.VK_B);
		graficoBarrasButton.setBackground(new Color(255, 255, 255));
		contentPane.add(graficoBarrasButton, "cell 6 3,alignx left,aligny top");
		
		graficoColunasButton = new JButton("Gráfico em Colunas");
		graficoColunasButton.setMnemonic(KeyEvent.VK_C);
		graficoColunasButton.setBackground(new Color(255, 255, 255));
		contentPane.add(graficoColunasButton, "cell 8 3,alignx left,aligny top");
		
		fecharButton = new JButton("Fechar");
		fecharButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharJanela(igPlanejamentoFinanceiro);
			}
		});
		fecharButton.setBackground(new Color(255, 255, 255));
		fecharButton.setMnemonic(KeyEvent.VK_F);
		contentPane.add(fecharButton, "cell 10 3,alignx left,aligny top");
		
		if (investimentoLista.tamanhoLista() > 0) {
			preencherTabela(investimentoLista);
			totalValorInvestido(investimentoLista);
			totalAcumulado(investimentoLista);
			rendimentoBruto(investimentoLista);
		}
		
		setVisible(true);
	}
	
	private void preencherTabela(InvestimentoLista investimentoLista) {
		int indice = -1;
		
		for (var investimento : investimentoLista) {
			indice++;
			Object[] dadosInvestimento = investimento.toList();
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[0], indice, 0);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[1], indice, 1);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[2], indice, 2);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[3], indice, 3);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[4], indice, 4);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[5], indice, 5);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[6], indice, 6);
			defaultTableModelInvestimento.setValueAt(dadosInvestimento[7], indice, 7);
			
			if (defaultTableModelInvestimento.getRowCount() == indice + 1)
				adicionarLinha();
			}
	}

	private void adicionarLinha() {
		defaultTableModelInvestimento.addRow(new Object[] {null, null, null, null, null, null, null, null});
	}

	private void fecharJanela(IgPlanejamentoFinanceiro igPlanejamentoFinanceiro) {
		this.dispose();
		igPlanejamentoFinanceiro.setVisible(true);
	}
	
	private void totalValorInvestido(InvestimentoLista investimentoLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		
		totalInvestidoTextField.setText(formatPreco.format(calculaTotalInvestido(investimentoLista)));
	}

	private Double calculaTotalInvestido(InvestimentoLista investimentoLista) {
		Double totalInvestido = 0D;
		
		for (var investimento : investimentoLista)
			totalInvestido += investimento.getValorInvestido();
		
		return totalInvestido;
	}
	
	private void totalAcumulado(InvestimentoLista investimentoLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		
		totalAcumuladoTextField.setText(formatPreco.format(calculaTotalAcumulado(investimentoLista)));
	}

	private Double calculaTotalAcumulado(InvestimentoLista investimentoLista) {
		Double totalAcumulado = 0D;
		
		for (var investimento : investimentoLista)
			totalAcumulado += investimento.getPosicao();
		
		return totalAcumulado;
	}
	
	private void rendimentoBruto(InvestimentoLista investimentoLista) {
		NumberFormat formatPreco = NumberFormat.getCurrencyInstance(Locale.of(PlanejamentoFinanceiro.PT, PlanejamentoFinanceiro.BR));
		
		rendimentoBrutoTextField.setText(formatPreco.format(calculaRendimentoBruto(investimentoLista)));
	}
	
	private Double calculaRendimentoBruto(InvestimentoLista investimentoLista) {
		Double totalRendimento = 0D;
		
		for (var investimento : investimentoLista)
			totalRendimento += investimento.getRendimentoBruto();
		
		return totalRendimento;
	}
}
