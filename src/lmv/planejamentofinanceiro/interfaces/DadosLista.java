package lmv.planejamentofinanceiro.interfaces;

public interface DadosLista<E>{
	
	public static final String VALOR_INVALIDO = "Valor inválido!";
	
	void criar();
	boolean inserir(E objeto);
	int pesquisar(int codigo);
	E obter(int indice);
	boolean alterar(int indice, E objeto);
	boolean alterar(E objeto);
	int tamanhoLista();
}
