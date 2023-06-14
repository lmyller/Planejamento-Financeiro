package lmv.planejamentofinanceiro.gui;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

public class IgPesquisarDespesa extends JFrame {

	private JPanel contentPane;
	private JTextField itemTextField;

	/**
	 * Create the frame.
	 */
	public IgPesquisarDespesa() {
		setBackground(new Color(255, 255, 255));
		setResizable(false);
		IgPlanejamentoFinanceiro.alterarLookAndFell("Nimbus");
		setTitle("Pesquisar Despesa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		itemLabel.setLabelFor(itemTextField);
		contentPane.add(itemTextField, "cell 1 0 5 1,growx,aligny top");
		itemTextField.setColumns(10);
		
		JLabel procurarLabel = new JLabel("Procurar por:");
		contentPane.add(procurarLabel, "cell 0 1,growx,aligny center");
		
		JRadioButton dataRadioButton = new JRadioButton("Data");
		dataRadioButton.setBackground(new Color(255, 255, 255));
		dataRadioButton.setSelected(true);
		dataRadioButton.setMnemonic(KeyEvent.VK_D);
		contentPane.add(dataRadioButton, "cell 1 1,growx,aligny top");
		
		JRadioButton descricaoRadioButton = new JRadioButton("Descrição");
		descricaoRadioButton.setBackground(new Color(255, 255, 255));
		descricaoRadioButton.setMnemonic(KeyEvent.VK_E);
		contentPane.add(descricaoRadioButton, "cell 3 1,growx,aligny top");
		
		JRadioButton valorRadioButton = new JRadioButton("Valor");
		valorRadioButton.setBackground(new Color(255, 255, 255));
		valorRadioButton.setMnemonic(KeyEvent.VK_V);
		contentPane.add(valorRadioButton, "cell 5 1 3 1,growx,aligny top");
		
		JButton proximaDespesaButton = new JButton("Próxima despesa");
		proximaDespesaButton.setMnemonic(KeyEvent.VK_P);
		contentPane.add(proximaDespesaButton, "cell 3 2 3 1,alignx right,aligny top");
		
		JButton fecharButton = new JButton("Fechar");
		fecharButton.setMnemonic(KeyEvent.VK_F);
		contentPane.add(fecharButton, "cell 7 2,alignx left,aligny top");
		
		setVisible(true);
	}
}
