package lmv.planejamentofinanceiro.interfaces;

public interface DadosLista<E, T> {
	void criar();
	boolean inserir(E objeto);
	int pesquisar(String string, T tipoDado);
	int pesquisar(int codigo);
	E obter(int indice);
	boolean alterar(int indice, E objeto);
	boolean alterar(E objeto);
	int tamanhoLista();
}
