package lmv.planejamentofinanceiro.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {
	 void adicionar(T objeto) throws SQLException;
	 T obter(T objeto) throws SQLException;
	 boolean alterar(T objeto) throws SQLException;
	 List<T> lista() throws SQLException;
}
