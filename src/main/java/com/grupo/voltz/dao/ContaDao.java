package com.grupo.voltz.dao;

import com.grupo.voltz.model.Conta;
import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaDao {

    private final Connection conexao;

    public ContaDao() throws SQLException{
        conexao = ConnectionService.getConnection();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrar(Conta conta) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Conta (id_conta, nomeinvestidor, emailInvestidor, senhaInvestidor, saldo, carteiras, movimentacoes ) VALUES (seq_produto.nextval,?,?,?,?,?,?)");
        stm.setString(1, conta.getNomeInvestidor());
        stm.setString(2, conta.getEmailInvestidor());
        stm.setString(3, conta.getSenhaInvestidor());
        stm.setDouble(4, conta.getSaldo());
        stm.setInt(5, conta.getCarteiras().size());
        stm.setInt(6, conta.getMovimentacoes().size());
        stm.executeUpdate();
    }
    public boolean pesquisar_email(String email) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM conta WHERE emailInvestidor = ?");
        stm.setString(1, email);
        ResultSet result = stm.executeQuery();
        return result.next(); // Retorna true se encontrar um registro com o email
    }

}
