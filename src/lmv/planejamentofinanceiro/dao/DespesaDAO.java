package lmv.planejamentofinanceiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.Categoria;
import lmv.planejamentofinanceiro.modelo.Despesa;

public class DespesaDAO implements DAO<Despesa> {
	
	private Connection connection;

	public DespesaDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adicionar(Despesa despesa) throws SQLException {
		String sql = "insert into despesa(descricao, cod_categoria) values (?, ?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, despesa.getDescricao());
			stmt.setInt(2, despesa.getCategoria().getCodigo());
			stmt.execute();
		}
	}
	
	public Integer obterCodigo(Despesa despesa) throws SQLException {
		String sql = "select codigo from despesa where descricao=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, despesa.getDescricao());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("codigo");
			}
		}
		return null;
	}

	@Override
	public Despesa obter(Despesa despesa) throws SQLException {
		CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
		String sql = "select * from despesa where descricao=?";
		Despesa despesaEncontrada = null;
		
		if (despesa.getCodigo() != null)
			sql = "select * from despesa where codigo=?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			if (despesa.getCodigo() == null)
				stmt.setString(1, despesa.getDescricao());
			
			else
				stmt.setInt(1, despesa.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				despesaEncontrada = new Despesa(rs.getString("descricao"), categoriaDAO.obter(obterCategoria(rs)));
				despesaEncontrada.setCodigo(rs.getInt("codigo"));
			}
		}
		return despesaEncontrada;
	}

	private Categoria obterCategoria(ResultSet rs) throws SQLException {
		Categoria categoria = new Categoria();
		categoria.setCodigo(rs.getInt("cod_categoria"));
		
		return categoria;
	}

	@Override
	public boolean alterar(Despesa despesa) throws SQLException {
		String sql = "update despesa set descricao=?, cod_categoria=? where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, despesa.getDescricao());
			stmt.setInt(2, despesa.getCategoria().getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<Despesa> lista() throws SQLException {
		return null;
	}
}
