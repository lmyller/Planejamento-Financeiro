package lmv.planejamentofinanceiro;

import javax.swing.UIManager;

import lmv.planejamentofinanceiro.gui.IgPlanejamentoFinanceiro;
import lmv.planejamentofinanceiro.lista.InvestimentoLista;
import lmv.planejamentofinanceiro.lista.OrcamentoLista;

public class PlanejamentoFinanceiro {
	
	public static final String TITULO = "Planejamento Financeiro";
											
	public static void main(String[] args) {
		alterarLookAndFell("Nimbus");
		new PlanejamentoFinanceiro();
	}
	
	public PlanejamentoFinanceiro() {
		OrcamentoLista orcamentoLista = new OrcamentoLista();
		InvestimentoLista investimentoLista = new InvestimentoLista();
		
		new IgPlanejamentoFinanceiro(orcamentoLista, investimentoLista);
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
}
