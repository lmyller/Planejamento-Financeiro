package lmv.planejamentofinanceiro.dao;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarRenda;
import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarRendaMensal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.RendaMensal;

public class RendaMensalDAO implements DAO<RendaMensal> {

	private Connection connection;
	
	public RendaMensalDAO(Connection connection) throws SQLException{
		this.connection = connection;
	}

	@Override
	public void adicionar(RendaMensal rendaMensal) throws SQLException {
		String sql = "insert into renda_mensal (cod_renda, data, valor) values (?, ?, ?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, rendaMensal.getCodigo());
			stmt.setDate(2, Date.valueOf(rendaMensal.getData()));
			stmt.setDouble(3, rendaMensal.getValor());
			stmt.execute();
		}
	}

	@Override
	public RendaMensal obter(RendaMensal rendaMensal) throws SQLException {
		RendaMensal rendaMensalEncontrada = null;
		RendaDAO rendaDAO = new RendaDAO(connection);
		String sql = "select * from renda_mensal where cod_renda=? and data=?";
		
		if (rendaMensal == null)
			return null;
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setLong(1, rendaMensal.getCodigo());
			stmt.setDate(2, Date.valueOf(rendaMensal.getData()));
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				rendaMensalEncontrada = criarRendaMensal(rendaDAO.obter(rendaMensal).getDescricao(), rs.getDate("data").toLocalDate(), rs.getDouble("valor"));
				rendaMensalEncontrada.setCodigo(rs.getInt("cod_renda"));
			}
		}
		return rendaMensalEncontrada;
	}

	@Override
	public boolean alterar(RendaMensal rendaMensal) throws SQLException {
		String sql = "update renda_mensal set data=?, valor=? where cod_renda=? and data=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setDate(1, Date.valueOf(rendaMensal.getData()));
			stmt.setDouble(2, rendaMensal.getValor());
			stmt.setInt(3, rendaMensal.getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<RendaMensal> lista() throws SQLException {
		List<RendaMensal> rendas = new ArrayList<>();
		RendaDAO rendaDAO = new RendaDAO(connection);
		String sql = "select * from renda_mensal";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				RendaMensal rendaMensal = criarRendaMensal(rendaDAO.obter(criarRenda(rs.getInt("cod_renda"), null)).getDescricao(), 
																								 rs.getDate("data").toLocalDate(), rs.getDouble("valor"));
				rendaMensal.setCodigo(rs.getInt("cod_renda"));
				rendas.add(rendaMensal);
			}
		}
		return rendas;
	}
}
