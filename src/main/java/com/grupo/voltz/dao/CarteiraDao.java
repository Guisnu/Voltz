package com.grupo.voltz.dao;

import com.grupo.voltz.model.Carteira;
import com.grupo.voltz.model.Conta;
import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarteiraDao {

    private final Connection conexao;

    public CarteiraDao() throws SQLException {
        this.conexao = ConnectionService.getConnection();
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    public void cadastrar(int idconta, Carteira carteira) throws SQLException {
        String sql = "INSERT INTO Carteira (idConta, nome, criptoativos, saldo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setLong(1, idconta);
            stm.setString(2, carteira.getNome());
            stm.setInt(3, carteira.getCriptoativos().size());
            stm.setDouble(4, carteira.getSaldo());
            stm.executeUpdate();
        }
    }


    public void recuperarCarteiras(Conta conta) throws SQLException {
        String sql = "SELECT * FROM Carteira WHERE idConta = ?";

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, conta.getIdConta());
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Carteira carteira = parseCarteira(rs);
                    System.out.println(carteira.getNome());
                    conta.adicionarCarteira(carteira); // Adiciona a carteira à conta
                }
            }
        }

    }


    /**
     * Atualiza o nome de uma carteira.
     */
    public void atualizarNome(String nomeAtualCarteira, String novoNome) throws SQLException {
        String sql = "UPDATE Carteira SET nome = ? WHERE nome = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, novoNome);
            stm.setString(2, nomeAtualCarteira);
            stm.executeUpdate();
        }
    }

    /**
     * Atualiza o saldo de uma carteira.
     */
    public void atualizarSaldo(Long idCarteira, double novoSaldo) throws SQLException {
        String sql = "UPDATE Carteira SET saldo = ? WHERE idCarteira = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, novoSaldo);
            stm.setLong(2, idCarteira);
            stm.executeUpdate();
        }
    }

    /**
     * Remove uma carteira do banco de dados.
     */
    public void deletar(Long idCarteira) throws SQLException {
        String sql = "DELETE FROM Carteira WHERE idCarteira = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setLong(1, idCarteira);
            stm.executeUpdate();
        }
    }

    public Integer buscarIdCarteiraPorNome(String nomeCarteira) throws SQLException {
        String sql = "SELECT idCarteira FROM Carteira WHERE nome = ?";

        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, nomeCarteira);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idCarteira");
                }
            }
        }

        return null; // retorna null se não encontrar nenhuma carteira com esse nome
    }

    private Carteira parseCarteira(ResultSet rs) throws SQLException {
        int idCarteira = rs.getInt("idCarteira");
        String nome = rs.getString("nome");
        double saldo = rs.getDouble("saldo");

        Carteira carteira = new Carteira(idCarteira, nome);
        carteira.setSaldo(saldo);

        return carteira;
    }
}
