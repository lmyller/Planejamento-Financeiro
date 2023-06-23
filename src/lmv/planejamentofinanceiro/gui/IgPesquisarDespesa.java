package lmv.planejamentofinanceiro.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import lmv.planejamentofinanceiro.Arquivo;
import lmv.planejamentofinanceiro.PlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.enumeracao.TipoDadoPesquisaDespesa;
import lmv.planejamentofinanceiro.interfaces.DadosLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;
import lmv.planejamentofinanceiro.validacao.ValidacaoData;
import mos.es.InputOutput;
import net.miginfocom.swing.MigLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IgPesquisarDespesa extends JDialog {

	private JPanel contentPane;
	private JTextField itemTextField;
	private JRadioButton dataRadioButton;
	private JRadioButton descricaoRadioButton;
	private JRadioButton valorRadioButton;
	private int indiceDespesa;

	/**
	 * Create the frame.
	 * @param orcamentoLista 
	 * @param igPlanejamentoFinanceiro 
	 */
	public IgPesquisarDespesa(IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, OrcamentoLista orcamentoLista) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fecharJanela();
			}
		});
		setBackground(new Color(255, 255, 255));
		setResizable(false);
		setTitle("Pesquisar Despesa");
		setBounds(100, 100, 437, 155);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[80px][48px][8px][96px][2px][94px][4px][63px]", "[19px][21px][21px]"));
		
		JLabel itemLabel = new JLabel("Item de despesa:");
		itemLabel.setDisplayedMnemonic(KeyEvent.VK_I);
		contentPane.add(itemLabel, "cell 0 0,alignx left,aligny center");
		
		itemTextField = new JTextField();
		itemTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indiceDespesa = 0;
				encontrarDespesa(orcamentoLista, igPlanejamentoFinanceiro, indiceDespesa);
			}
		});
		
		itemLabel.setLabelFor(itemTextField);
		contentPane.add(itemTextField, "cell 1 0 5 1,growx,aligny top");
		itemTextField.setColumns(10);
		
		JLabel procurarLabel = new JLabel("Procurar por:");
		contentPane.add(procurarLabel, "cell 0 1,growx,aligny center");
		
		dataRadioButton = new JRadioButton("Data");
		dataRadioButton.setBackground(new Color(255, 255, 255));
		dataRadioButton.setSelected(true);
		dataRadioButton.setMnemonic(KeyEvent.VK_D);
		contentPane.add(dataRadioButton, "cell 1 1,growx,aligny top");
		
		descricaoRadioButton = new JRadioButton("Descrição");
		descricaoRadioButton.setBackground(new Color(255, 255, 255));
		descricaoRadioButton.setMnemonic(KeyEvent.VK_E);
		contentPane.add(descricaoRadioButton, "cell 3 1,growx,aligny top");
		
		valorRadioButton = new JRadioButton("Valor");
		valorRadioButton.setBackground(new Color(255, 255, 255));
		valorRadioButton.setMnemonic(KeyEvent.VK_V);
		contentPane.add(valorRadioButton, "cell 5 1 3 1,growx,aligny top");
		
		JButton proximaDespesaButton = new JButton("Próxima despesa");
		proximaDespesaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				indiceDespesa++;
				encontrarDespesa(orcamentoLista, igPlanejamentoFinanceiro, indiceDespesa);
			}
		});
		proximaDespesaButton.setMnemonic(KeyEvent.VK_P);
		contentPane.add(proximaDespesaButton, "cell 3 2 3 1,alignx right,aligny top");
		
		JButton fecharButton = new JButton("Fechar");
		fecharButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecharJanela();
			}
		});
		fecharButton.setMnemonic(KeyEvent.VK_F);
		contentPane.add(fecharButton, "cell 7 2,alignx left,aligny top");
		itemTextField.requestFocus();
		setVisible(true);
	}
	
	private void encontrarDespesa(OrcamentoLista orcamentoLista, IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, int indiceDespesa) {
		Float valor = null;
		
		if (itemTextField != null) {
			if (dataRadioButton.isSelected() && new ValidacaoData().valida(itemTextField.getText()))
				if (orcamentoLista.pesquisar(itemTextField.getText(), TipoDadoPesquisaDespesa.DATA) != -1)
					buscarTabelaData(itemTextField.getText(), igPlanejamentoFinanceiro, indiceDespesa);
			
			if (descricaoRadioButton.isSelected())
				if (orcamentoLista.pesquisar(itemTextField.getText(), TipoDadoPesquisaDespesa.DESCRICAO) != -1)
					buscarTabelaDescricao(itemTextField.getText(), igPlanejamentoFinanceiro, indiceDespesa);
			
			if (valorRadioButton.isSelected())
				if (orcamentoLista.pesquisar(itemTextField.getText(), TipoDadoPesquisaDespesa.VALOR) != -1)
					valor = Arquivo.converterNumeroReal(itemTextField.getText());
					buscarTabelaValor(valor, igPlanejamentoFinanceiro, indiceDespesa);
		}
		
		else
			InputOutput.showError(ValidacaoData.DATA_INVALIDA, PlanejamentoFinanceiro.TITULO);
	}

	private void buscarTabelaData(String data, IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, int indiceDespesa) {
		DefaultTableModel defaultTableModelOrcamento = igPlanejamentoFinanceiro.defaultTableModelOrcamento;
		for (int indice = indiceDespesa; indice < defaultTableModelOrcamento.getRowCount(); indice++) {
			if (defaultTableModelOrcamento.getValueAt(indice, IgPlanejamentoFinanceiro.COLUNA_DATA).toString().equalsIgnoreCase(data)) {
				igPlanejamentoFinanceiro.getOrcamentoTable().setRowSelectionInterval(indice, IgPlanejamentoFinanceiro.COLUNA_DATA);
				indiceDespesa = indice;
			}
		}
	}
	
	private void buscarTabelaDescricao(String descricao, IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, int indiceDespesa) {
		DefaultTableModel defaultTableModelOrcamento = igPlanejamentoFinanceiro.defaultTableModelOrcamento;
		for (int indice = indiceDespesa; indice < defaultTableModelOrcamento.getRowCount(); indice++) {
			if (defaultTableModelOrcamento.getValueAt(indice, IgPlanejamentoFinanceiro.COLUNA_DESCRICAO).toString().equalsIgnoreCase(descricao)) {
				igPlanejamentoFinanceiro.getOrcamentoTable().setRowSelectionInterval(indice, IgPlanejamentoFinanceiro.COLUNA_DESCRICAO);
				indiceDespesa = indice;
			}
		}
	}
	
	private void buscarTabelaValor(Float valor, IgPlanejamentoFinanceiro igPlanejamentoFinanceiro, int indiceDespesa) {
		Float valorTabela = null;
		DefaultTableModel defaultTableModelOrcamento = igPlanejamentoFinanceiro.defaultTableModelOrcamento;
		for (int indice = indiceDespesa; indice < defaultTableModelOrcamento.getRowCount(); indice++) {
			valorTabela = Arquivo.converterNumeroReal(defaultTableModelOrcamento.getValueAt(indice, IgPlanejamentoFinanceiro.COLUNA_VALOR).toString());
			if(valorTabela == null) {
				InputOutput.showError(DadosLista.VALOR_INVALIDO, getTitle());
				return;
			}
			
			if (valorTabela == valor) {
				igPlanejamentoFinanceiro.getOrcamentoTable().setRowSelectionInterval(indice, IgPlanejamentoFinanceiro.COLUNA_VALOR);
				indiceDespesa = indice;
			}
		}
	}
	
	private void fecharJanela() {
		dispose();
	}
}
