package com.grupo.voltz.dao;

import com.grupo.voltz.model.Transacao;
import com.grupo.voltz.model.Criptoativo;
import com.grupo.voltz.enums.TipoTransacaoEnum;
import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public void cadastrar(Transacao transacao) throws SQLException {
        // Supondo que existe uma sequência 'seq_transacao' para gerar id_transacao
        String sql = "INSERT INTO transacao (id_transacao, tipo, criptoativo_id, quantidade, valor, data_transacao)" +
                "VALUES (seq_transacao.nextval, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);

        // Converte o enum para String
        stm.setString(1, transacao.getTipo().name());
        // Utiliza o id do criptoativo associado (é esperado que Criptoativo tenha o método getId())
        stm.setInt(2, transacao.getCriptoativo().getId());
        stm.setDouble(3, transacao.getQuantidade());
        stm.setDouble(4, transacao.getValor());
        // Converte LocalDateTime para Timestamp
        stm.setTimestamp(5, Timestamp.valueOf(transacao.getData()));

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