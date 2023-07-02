package lmv.planejamentofinanceiro.dao;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarFormaPagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.FormaPagamento;

public class FormaPagamentoDAO implements DAO<FormaPagamento> {

	private Connection connection;
	
	public FormaPagamentoDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adicionar(FormaPagamento formaPagamento) throws SQLException {
		String sql = "insert into forma_pagamento(descricao) values (?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, formaPagamento.getDescricao());
			stmt.execute();
		}
	}
	
	public Integer obterCodigo(FormaPagamento formaPagamento) throws SQLException {
		String sql = "select codigo from forma_pagamento where descricao=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, formaPagamento.getDescricao());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				return rs.getInt("codigo");
			}
		}
		return null;
	}
	
	@Override
	public FormaPagamento obter(FormaPagamento formaPagamento) throws SQLException {
		String sql = "select * from forma_pagamento where descricao=?";
		FormaPagamento formaPagamentoEncontrado = null;
		
		if (formaPagamento.getCodigo() != null)
			sql = "select * from forma_pagamento where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			
			if (formaPagamento.getCodigo() == null)
				stmt.setString(1, formaPagamento.getDescricao());
			
			else
				stmt.setInt(1, formaPagamento.getCodigo());
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				formaPagamentoEncontrado = criarFormaPagamento(rs.getString("descricao"));
				formaPagamentoEncontrado.setCodigo(rs.getInt("codigo"));
			}
		}
		return formaPagamentoEncontrado;
	}

	@Override
	public boolean alterar(FormaPagamento formaPagamento) throws SQLException {
		String sql = "update forma_pagamento set descricao=? where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, formaPagamento.getDescricao());
			stmt.setInt(2, formaPagamento.getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<FormaPagamento> lista() throws SQLException {
		List<FormaPagamento> formasPagamento = new ArrayList<>();
		String sql = "select * from forma_pagamento";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				FormaPagamento formaPagamento = criarFormaPagamento(rs.getString("descricao"));
				formaPagamento.setCodigo(rs.getInt("codigo"));
				formasPagamento.add(formaPagamento);
			}
		}
		return formasPagamento;
	}
}
