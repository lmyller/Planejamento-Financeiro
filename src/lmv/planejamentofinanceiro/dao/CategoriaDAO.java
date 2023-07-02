package lmv.planejamentofinanceiro.dao;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarCategoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.Categoria;

public class CategoriaDAO implements DAO<Categoria> {

	private Connection connection;
	
	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void adicionar(Categoria categoria) throws SQLException {
		String sql = "insert into categoria(descricao) values (?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, categoria.getDescricao());
			stmt.execute();
		}
	}
	
	public Integer obterCodigo(Categoria categoria) throws SQLException {
		String sql = "select codigo from categoria where descricao=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, categoria.getDescricao());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("codigo");
			}
		}
		return null;
	}

	@Override
	public Categoria obter(Categoria categoria) throws SQLException {
		String sql = "select * from categoria where descricao=?";
		Categoria categoriaEncontrada = null;
		
		if (categoria.getCodigo() != null)
			sql = "select * from categoria where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			
			if (categoria.getCodigo() == null)
				stmt.setString(1, categoria.getDescricao());
			
			else
				stmt.setInt(1, categoria.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				categoriaEncontrada = criarCategoria(rs.getString("descricao"));
				categoriaEncontrada.setCodigo(rs.getInt("codigo"));
			}
		}
		return categoriaEncontrada;
	}

	@Override
	public boolean alterar(Categoria categoria) throws SQLException {
		String sql = "update categoria set descricao=? where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, categoria.getDescricao());
			stmt.setInt(2, categoria.getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<Categoria> lista() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "select * from categoria";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Categoria categoria = criarCategoria(rs.getString("descricao"));
				categoria.setCodigo(rs.getInt("codigo"));
				categorias.add(categoria);
			}
		}
		return categorias;
	}
}
