package com.grupo.voltz.dao;

import com.grupo.voltz.model.Conta;
import com.grupo.voltz.model.Transacao;
import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Timestamp;


public class TransacaoDao {

    private final Connection conexao;

    // Construtor que obtém a conexão via ConnectionService
    public TransacaoDao() throws SQLException {
        conexao = ConnectionService.getConnection();
    }

    // Método para fechar a conexão
    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    // Método para cadastrar (inserir) uma transação no banco
    public void cadastrar(Transacao transacao, Integer idconta) throws SQLException {
        // Supondo que existe uma sequência 'seq_transacao' para gerar id_transacao
        String sql = "INSERT INTO transacao (tipo, idCriptoAtivo, quantidade, valor, data, idconta)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);

        stm.setString(1, transacao.getTipo().name());
        stm.setInt(2,transacao.getCriptoativo().getIdCriptoativo());
        stm.setDouble(3, transacao.getQuantidade());
        stm.setDouble(4, transacao.getValor());
        stm.setTimestamp(5, Timestamp.valueOf(transacao.getData()));
        stm.setInt(6, idconta);
        stm.executeUpdate();
    }


    // Método para deletar uma transação pelo id (caso necessário)
    public void deletarTransacao(Long idTransacao) throws SQLException {
        String sql = "DELETE FROM transacao WHERE id_transacao = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, idTransacao);
        stm.executeUpdate();
    }
}