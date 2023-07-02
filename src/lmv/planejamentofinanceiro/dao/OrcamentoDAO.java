package lmv.planejamentofinanceiro.dao;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.criarOrcamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.MonthDay;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.Despesa;
import lmv.planejamentofinanceiro.modelo.FormaPagamento;
import lmv.planejamentofinanceiro.modelo.Orcamento;

public class OrcamentoDAO implements DAO<Orcamento> {

	private Connection connection;

	public OrcamentoDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adicionar(Orcamento orcamento) throws SQLException {
		String sql = "insert into orcamento(mes_ano, cod_despesa, data_despesa, data_pagamento, cod_forma_pagamento, valor, situacao) "
						+ "values (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setShort(1, orcamento.getMesAno());
			stmt.setInt(2, orcamento.getDespesa().getCodigo());
			stmt.setDate(3, Date.valueOf(orcamento.getDataDespesa()));
			stmt.setString(4, orcamento.getDataPagamento().toString());
			stmt.setInt(5, orcamento.getFormaPagamento().getCodigo());
			stmt.setDouble(6, orcamento.getValor());
			stmt.setBoolean(7, orcamento.isSituacao());
			stmt.execute();
		}
	}

	@Override
	public Orcamento obter(Orcamento orcamento) throws SQLException {
		DespesaDAO despesaDAO = new DespesaDAO(connection);
		FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO(connection);
		String sql = "select * from orcamento where mes_ano=? and cod_despesa=?";
		Orcamento orcamentoEncontrado = null;
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setShort(1, orcamento.getMesAno());
			stmt.setInt(2, orcamento.getDespesa().getCodigo());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) 
				orcamentoEncontrado = criarOrcamento(rs.getShort("mes_ano"), rs.getDate("data_despesa").toLocalDate(),MonthDay.parse(rs.getString("data_pagamento")), 
																					rs.getDouble("valor"), despesaDAO.obter(obterDespesa(rs)), formaPagamentoDAO.obter(obterFormaPagamento(rs)), 
																					rs.getBoolean("situacao"));
			 
		}
		return orcamentoEncontrado;
	}

	private FormaPagamento obterFormaPagamento(ResultSet rs) throws SQLException {
		FormaPagamento formaPagamento = new FormaPagamento();
		formaPagamento.setCodigo(rs.getInt("cod_forma_pagamento"));
		
		return formaPagamento;
	}

	private Despesa obterDespesa(ResultSet rs) throws SQLException {
		Despesa despesa = new Despesa();
		despesa.setCodigo(rs.getInt("cod_despesa"));
		
		return despesa;
	}

	@Override
	public boolean alterar(Orcamento orcamento) throws SQLException {
		String sql = "update orcamento set data_despesa=?, data_pagamento=?, cod_forma_pagamento=?, "
						+ "valor=?, situacao=? where mes_ano=?, cod_despesa=?";

		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setDate(1, Date.valueOf(orcamento.getDataDespesa()));
			stmt.setString(2, orcamento.getDataPagamento().toString());
			stmt.setInt(3, orcamento.getFormaPagamento().getCodigo());
			stmt.setDouble(4, orcamento.getValor());
			stmt.setBoolean(5, orcamento.isSituacao());
			stmt.setShort(6, orcamento.getMesAno());
			stmt.setInt(6, orcamento.getDespesa().getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<Orcamento> lista() throws SQLException {
		DespesaDAO despesaDAO = new DespesaDAO(connection);
		FormaPagamentoDAO formaPagamentoDAO = new FormaPagamentoDAO(connection);
		List<Orcamento> orcamentos = new ArrayList<>();
		String sql = "select * from orcamento";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Orcamento orcamento = criarOrcamento(rs.getShort("mes_ano"), rs.getDate("data_despesa").toLocalDate(),MonthDay.parse(rs.getString("data_pagamento")), 
																					rs.getDouble("valor"), despesaDAO.obter(obterDespesa(rs)), formaPagamentoDAO.obter(obterFormaPagamento(rs)), 
																					rs.getBoolean("situacao"));
				orcamentos.add(orcamento);
			}
		}
		return orcamentos;
	}
}
