package lmv.planejamentofinanceiro.interfaces;

public interface Validacao <T>{
	
	static String ERRO_VALIDACAO = "Erro validação";
	
	boolean valida(T validar);
	boolean valida(T validar, String regex);
}
