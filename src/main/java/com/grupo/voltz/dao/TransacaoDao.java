package com.grupo.voltz.dao;

import com.grupo.voltz.enums.TipoTransacaoEnum;
import com.grupo.voltz.model.Conta;
import com.grupo.voltz.model.Criptoativo;
import com.grupo.voltz.model.Transacao;
import com.grupo.voltz.services.ConnectionService;

import java.sql.*;

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
    public void cadastrar(Transacao transacao, Integer idconta) throws SQLException {
        // Supondo que existe uma sequência 'seq_transacao' para gerar id_transacao
        String sql = "INSERT INTO transacao (tipo, idCriptoAtivo, quantidade, valor, data, idconta)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = conexao.prepareStatement(sql);

        stm.setString(1, transacao.getTipo().name());
        stm.setInt(2,transacao.getCriptoativo().getIdCriptoativo());
        stm.setDouble(3, transacao.getQuantidade());
        stm.setDouble(4, transacao.getValor());
        stm.setDate(5, Date.valueOf(transacao.getData()));
        stm.setInt(6, idconta);
        stm.executeUpdate();
    }


    // Método para deletar uma transação pelo id (caso necessário)
    public void deletarTransacao(Integer idTransacao) throws SQLException {
        String sql = "DELETE FROM transacao WHERE idtransacao = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setLong(1, idTransacao);
        stm.executeUpdate();
    }

    public List<Transacao> listarPorConta(int idConta) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();

        String sql = """
        SELECT t.idTransacao, t.tipo, t.valor, t.quantidade, t.data,
               c.idCriptoAtivo, c.nome, c.simbolo, c.precoAtual
        FROM Transacao t
        JOIN Criptoativo c ON t.idCriptoAtivo = c.idCriptoAtivo
        WHERE t.idConta = ?
        ORDER BY t.data DESC
    """;

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idConta);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Criptoativo cripto = new Criptoativo(
                        rs.getInt("idCriptoAtivo"),
                        rs.getString("nome"),
                        rs.getString("simbolo"),
                        rs.getDouble("precoAtual")
                );
                double quantidade = rs.getDouble("quantidade");
                double valor = rs.getDouble("valor");
                String tipo = rs.getString("tipo");
                Date data = rs.getDate("data");

                Transacao transacao = new Transacao(TipoTransacaoEnum.valueOf(tipo), cripto, quantidade, valor);
                transacao.setData(data.toLocalDate());
                transacao.setIdTransacao(rs.getInt("idTransacao"));

                transacoes.add(transacao);
            }
        }

        return transacoes;
    }

}