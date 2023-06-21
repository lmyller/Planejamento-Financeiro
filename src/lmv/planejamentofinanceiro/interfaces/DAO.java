package lmv.planejamentofinanceiro.interfaces;

public interface DAO<T> {
	 void adicionar(T objeto);
	 T obter(T objeto);
	 boolean atualizar(T objeto);
	 boolean deletar(T objeto);
}
