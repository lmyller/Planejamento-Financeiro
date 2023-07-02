package lmv.planejamentofinanceiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.Renda;
import lmv.planejamentofinanceiro.modelo.RendaMensal;

public class RendaDAO implements DAO<Renda> {

	private Connection connection;
	
	public RendaDAO(Connection connection) throws SQLException{
		this.connection = connection;
	}

	@Override
	public void adicionar(Renda renda) throws SQLException {
		String sql = "insert into renda(descricao) values (?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, renda.getDescricao());
			stmt.execute();
		}
	}
	
	public Integer obterCodigo(Renda renda) throws SQLException {
		String sql = "select codigo from renda where descricao=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, renda.getDescricao());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("codigo");
			}
		}
		return null;
	}

	@Override
	public Renda obter(Renda renda) throws SQLException {
		String sql = "select * from renda where descricao=?";
		Renda rendaEncontrada = null;
		
		if (renda.getCodigo() != null)
			sql = "select * from renda where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			if (renda.getCodigo() == null)
				stmt.setString(1, renda.getDescricao());
			
			else 
				stmt.setInt(1, renda.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rendaEncontrada = new RendaMensal();
				rendaEncontrada.setDescricao(rs.getString("descricao"));
				rendaEncontrada.setCodigo(rs.getInt("codigo"));
			}
		}
		return rendaEncontrada;
	}

	@Override
	public boolean alterar(Renda renda) throws SQLException {
		String sql = "update renda set descricao=? where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, renda.getDescricao());
			stmt.setInt(2, renda.getCodigo());;
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<Renda> lista() throws SQLException {
		List<Renda> rendas = new ArrayList<>();
		String sql = "select * from renda";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Renda renda = new RendaMensal();
				renda.setDescricao(rs.getString("descricao"));
				renda.setCodigo(rs.getInt("codigo"));
				rendas.add(renda);
			}
		return rendas;
		}
	}
}
