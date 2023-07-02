package lmv.planejamentofinanceiro.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lmv.planejamentofinanceiro.interfaces.DAO;
import lmv.planejamentofinanceiro.modelo.Investimento;

import static lmv.planejamentofinanceiro.PlanejamentoFinanceiro.*;

public class InvestimentoDAO implements DAO<Investimento> {

	private Connection connection;
	
	public InvestimentoDAO(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void adicionar(Investimento investimento) throws SQLException {
		String sql = "insert into investimento(objetivo, estrategia, nome, valor_investido, posicao,"
						+ "rendimento_bruto, rentabilidade, vencimento) values (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, investimento.getObjetivo());
			stmt.setString(2, investimento.getEstrategia());
			stmt.setString(3, investimento.getNome());
			stmt.setDouble(4, investimento.getValorInvestido());
			stmt.setDouble(5, investimento.getPosicao());
			stmt.setDouble(6, investimento.getRendimentoBruto());
			stmt.setDouble(7, investimento.getRentabilidade());
			stmt.setDate(8, Date.valueOf(investimento.getVencimento()));
			stmt.execute();
		}
	}

	@Override
	public Investimento obter(Investimento investimento) throws SQLException {
		String sql = "select * from investimento where codigo=?";
		Investimento investimentoEncontrado = null;
		
		if (investimento.getCodigo() == null)
			return null;
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setInt(1, investimento.getCodigo());
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				investimentoEncontrado = criarInvestimento(rs.getString("objetivo"), rs.getString("estrategia"), rs.getString("nome"), rs.getDouble("valor_investido"),
													  									   rs.getDouble("posicao"), rs.getDouble("rendimento_bruto"), rs.getDouble("rentabilidade"), 
													  									   rs.getDate("vencimento").toLocalDate());
				investimentoEncontrado.setCodigo(rs.getInt("codigo"));
			} 
		}
		return investimentoEncontrado;
	}
	
	@Override
	public boolean alterar(Investimento investimento) throws SQLException {
		String sql = "update investimento set objetivo=?, estrategia=?, nome=?, valor_investido=?, posicao=?,"
					     + "rendimento_bruto=?, rentabilidade=?, vencimento=? where codigo=?";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			stmt.setString(1, investimento.getObjetivo());
			stmt.setString(2, investimento.getEstrategia());
			stmt.setString(3, investimento.getNome());
			stmt.setDouble(4, investimento.getValorInvestido());
			stmt.setDouble(5, investimento.getPosicao());
			stmt.setDouble(6, investimento.getRendimentoBruto());
			stmt.setDouble(7, investimento.getRentabilidade());
			stmt.setDate(8, Date.valueOf(investimento.getEstrategia()));
			stmt.setInt(9, investimento.getCodigo());
			stmt.execute();
		}
		return true;
	}

	@Override
	public List<Investimento> lista() throws SQLException {
		List<Investimento> investimentos = new ArrayList<>();
		String sql = "select * from investimento";
		
		try (PreparedStatement stmt = connection.prepareStatement(sql)){
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Investimento investimento = criarInvestimento(rs.getString("objetivo"), rs.getString("estrategia"), rs.getString("nome"), rs.getDouble("valor_investido"),
																							  rs.getDouble("posicao"), rs.getDouble("rendimento_bruto"), rs.getDouble("rentabilidade"), 
																							  rs.getDate("vencimento").toLocalDate());
				investimento.setCodigo(rs.getInt("codigo"));
				investimentos.add(investimento);
			}
		}
		return investimentos;
	}
}
