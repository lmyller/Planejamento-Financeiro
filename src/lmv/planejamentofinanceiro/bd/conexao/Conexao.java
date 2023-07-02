package lmv.planejamentofinanceiro.bd.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao implements AutoCloseable{
	private Connection connection;

	public Conexao(String url, String usuario, String password) throws SQLException {
		connection = DriverManager.getConnection(url, usuario, password);
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void close() throws SQLException {
		connection.close();
	}
}
