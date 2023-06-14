package lmv.planejamentofinanceiro.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;

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
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import lmv.planejamentofinanceiro.enumeracao.Mes;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IgPlanejamentoFinanceiro extends JFrame {

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
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IgPlanejamentoFinanceiro frame = new IgPlanejamentoFinanceiro();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public IgPlanejamentoFinanceiro() {
		alterarLookAndFell("Nimbus");
		setResizable(false);
		setFont(new Font("Dialog", Font.PLAIN, 14));
		setTitle("Planejamento Financeiro");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1122, 490);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[147px,grow,center][147px,center][147px,center][147px,center][147px,center][147px,center][147px,center][147px,center]", "[23px][21px][348px,grow][grow]"));
		
		JLabel receitaLabel = new JLabel("Receitas");
		receitaLabel.setForeground(new Color(0, 0, 255));
		receitaLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(receitaLabel, "cell 0 0,alignx center,growy");
		
		JLabel despesasLabel = new JLabel("Despesas");
		despesasLabel.setForeground(new Color(0, 0, 255));
		despesasLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(despesasLabel, "cell 1 0,alignx center,aligny center");
		
		JLabel saldoLabel = new JLabel("Saldo");
		saldoLabel.setForeground(new Color(0, 0, 255));
		saldoLabel.setBackground(new Color(0, 0, 0));
		saldoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(saldoLabel, "cell 2 0,alignx center,aligny center");
		
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
		contentPane.add(despesaTextField, "cell 1 1,growx,aligny top");
		despesaTextField.setColumns(10);
		
		JLabel totalPagoLabel = new JLabel("Total Pago");
		totalPagoLabel.setForeground(new Color(0, 0, 255));
		totalPagoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(totalPagoLabel, "cell 4 0,alignx center,aligny center");
		
		saldoTextField = new JTextField();
		saldoTextField.setBorder(null);
		saldoTextField.setEditable(false);
		saldoTextField.setText("R$ 0");
		saldoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		saldoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(saldoTextField, "cell 2 1,growx,aligny top");
		saldoTextField.setColumns(10);
		
		totalPagoTextField = new JTextField();
		totalPagoTextField.setBorder(null);
		totalPagoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalPagoTextField.setText("R$ 0");
		totalPagoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalPagoTextField.setEditable(false);
		contentPane.add(totalPagoTextField, "cell 4 1,growx,aligny top");
		totalPagoTextField.setColumns(10);
		
		JLabel totalPagarLabel = new JLabel("Total A Pagar");
		totalPagarLabel.setForeground(new Color(0, 0, 255));
		totalPagarLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(totalPagarLabel, "cell 5 0,alignx center,aligny center");
		
		totalPagarTextField = new JTextField();
		totalPagarTextField.setHorizontalAlignment(SwingConstants.CENTER);
		totalPagarTextField.setText("R$ 0");
		totalPagarTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		totalPagarTextField.setEditable(false);
		totalPagarTextField.setBorder(null);
		contentPane.add(totalPagarTextField, "cell 5 1,growx,aligny bottom");
		totalPagarTextField.setColumns(10);
		
		JLabel investimentoLabel = new JLabel("Investimentos");
		investimentoLabel.setForeground(new Color(0, 0, 255));
		investimentoLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		contentPane.add(investimentoLabel, "cell 7 0,alignx center,aligny center");
		
		investimentoTextField = new JTextField();
		investimentoTextField.setEditable(false);
		investimentoTextField.setBorder(null);
		investimentoTextField.setHorizontalAlignment(SwingConstants.CENTER);
		investimentoTextField.setText("R$ 0");
		investimentoTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(investimentoTextField, "cell 7 1,growx,aligny top");
		investimentoTextField.setColumns(10);
		
		orcamentoPanel = new JPanel();
		orcamentoPanel.setBorder(new TitledBorder(null, "Or\u00E7amento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		orcamentoPanel.setBackground(new Color(255, 255, 255));
		contentPane.add(orcamentoPanel, "cell 0 2 8 1,grow");
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
		orcamentoScrollPane.setViewportView(orcamentoTable);
		orcamentoTable.setShowVerticalLines(true);
		orcamentoTable.setShowHorizontalLines(true);
		orcamentoTable.getTableHeader().setBackground(new Color(255, 255, 255));
		orcamentoTable.setBackground(new Color(255, 255, 255));
		orcamentoTable.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Data", "Dia", "Tipo", "Descri\u00E7\u00E3o", "Valor", "Paga"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Short.class, String.class, String.class, Double.class, Boolean.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		orcamentoTable.getColumnModel().getColumn(0).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(1).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(2).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(3).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(4).setResizable(false);
		orcamentoTable.getColumnModel().getColumn(5).setResizable(false);
		
		pesquisarDespesaButton = new JButton("Pesquisar Despesa...");
		pesquisarDespesaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new IgPesquisarDespesa();
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
		categoriaComboBox.setBounds(213, 25, 119, 26);
		categoriaLabel.setLabelFor(categoriaComboBox);
		categoriaComboBox.setBackground(new Color(255, 255, 255));
		orcamentoPanel.add(categoriaComboBox);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel, "cell 0 3 8 1,alignx right,growy");
		
		importarButton = new JButton("Importar...");
		panel.add(importarButton);
		importarButton.setMnemonic(KeyEvent.VK_O);
		importarButton.setBackground(new Color(255, 255, 255));
		
		investimentosButton = new JButton("Investimentos...");
		panel.add(investimentosButton);
		investimentosButton.setMnemonic(KeyEvent.VK_I);
		investimentosButton.setBackground(new Color(255, 255, 255));
	}

	protected static void alterarLookAndFell(String lookAndFeel) {
		for (var look : UIManager.getInstalledLookAndFeels()) {
			if (look.getName().equalsIgnoreCase(lookAndFeel)) {
				try {
					UIManager.setLookAndFeel(look.getClassName());
				} catch (Exception e){
					
				}
			}
		}		
	}
}
