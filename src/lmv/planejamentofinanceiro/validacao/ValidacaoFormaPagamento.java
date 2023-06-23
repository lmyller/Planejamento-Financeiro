package lmv.planejamentofinanceiro.validacao;

import lmv.planejamentofinanceiro.enumeracao.FormaPagamentoEnum;
import lmv.planejamentofinanceiro.interfaces.Validacao;

public class ValidacaoFormaPagamento implements Validacao<String>{

	@Override
	public boolean valida(String validar) {
		if (FormaPagamentoEnum.CARTAO_CREDITO.getSiglaPagamento().equalsIgnoreCase(validar))
			return true;
		
		if (FormaPagamentoEnum.CARTAO_DEBITO.getSiglaPagamento().equalsIgnoreCase(validar))
			return true;
		
		if (FormaPagamentoEnum.DINHEIRO.getSiglaPagamento().equalsIgnoreCase(validar))
			return true;
		
		if (FormaPagamentoEnum.PIX.getSiglaPagamento().equalsIgnoreCase(validar))
			return true;
		
		return false;
	}

	@Override
	public boolean valida(String validar, String regex) {
		return valida(validar);
	}

	public static String descricaoFormaPagamento(String siglaFormaPagamento) {
		if (siglaFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.CARTAO_DEBITO.getSiglaPagamento()))
			return FormaPagamentoEnum.CARTAO_DEBITO.getDescricaoPagamento();
		
		if (siglaFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.CARTAO_CREDITO.getSiglaPagamento()))
			return FormaPagamentoEnum.CARTAO_CREDITO.getDescricaoPagamento();
		
		if (siglaFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.DINHEIRO.getSiglaPagamento()))
			return FormaPagamentoEnum.DINHEIRO.getDescricaoPagamento();
		
		if (siglaFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.PIX.getSiglaPagamento()))
			return FormaPagamentoEnum.PIX.getDescricaoPagamento();
	
		return null;
	}
	
	public static String siglaFormaPagamento(String descricaoFormaPagamento) {
		if (descricaoFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.CARTAO_DEBITO.getDescricaoPagamento()))
			return FormaPagamentoEnum.CARTAO_DEBITO.getSiglaPagamento();
		
		if (descricaoFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.CARTAO_CREDITO.getDescricaoPagamento()))
			return FormaPagamentoEnum.CARTAO_CREDITO.getSiglaPagamento();
		
		if (descricaoFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.DINHEIRO.getDescricaoPagamento()))
			return FormaPagamentoEnum.DINHEIRO.getSiglaPagamento();
		
		if (descricaoFormaPagamento.equalsIgnoreCase(FormaPagamentoEnum.PIX.getDescricaoPagamento()))
			return FormaPagamentoEnum.PIX.getSiglaPagamento();
	
		return null;
	}
}
