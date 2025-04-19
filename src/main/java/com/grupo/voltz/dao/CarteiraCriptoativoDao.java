package com.grupo.voltz.dao;

import com.grupo.voltz.model.Criptoativo;
import com.grupo.voltz.services.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarteiraCriptoativoDao {
    private final Connection conexao;

    public CarteiraCriptoativoDao() throws SQLException {
        this.conexao = ConnectionService.getConnection();
    }

    /**
     * Registra ou atualiza uma compra de criptoativo
     */
    public void registrarCompra(int idCarteira, int idCriptoativo, double quantidade) throws SQLException {
        String select = "SELECT quantidade FROM Carteira_Criptoativo WHERE idCarteira = ? AND idCriptoAtivo = ?";
        try (PreparedStatement ps = conexao.prepareStatement(select)) {
            ps.setInt(1, idCarteira);
            ps.setInt(2, idCriptoativo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double atual = rs.getDouble("quantidade");
                String update = "UPDATE Carteira_Criptoativo SET quantidade = ? WHERE idCarteira = ? AND idCriptoAtivo = ?";
                try (PreparedStatement up = conexao.prepareStatement(update)) {
                    up.setDouble(1, atual + quantidade);
                    up.setInt(2, idCarteira);
                    up.setInt(3, idCriptoativo);
                    up.executeUpdate();
                }
            } else {
                String insert = "INSERT INTO Carteira_Criptoativo (idCarteira, idCriptoAtivo, quantidade) VALUES (?, ?, ?)";
                try (PreparedStatement ins = conexao.prepareStatement(insert)) {
                    ins.setInt(1, idCarteira);
                    ins.setInt(2, idCriptoativo);
                    ins.setDouble(3, quantidade);
                    ins.executeUpdate();
                }
            }
        }
    }

    /**
     * Lista todos os criptoativos e suas quantidades de uma carteira
     */
    public List<Criptoativo> listarCriptoativosPorCarteira(int idCarteira) throws SQLException {
        String sql = """
            SELECT c.idCriptoAtivo, c.nome, c.simbolo, c.precoAtual, cc.quantidade
            FROM Carteira_CriptoAtivo cc
            JOIN Criptoativo c ON cc.idCriptoAtivo = c.idCriptoAtivo
            WHERE cc.idCarteira = ?
        """;

        List<Criptoativo> criptoativos = new ArrayList<>();

        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCarteira);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Criptoativo cripto = new Criptoativo(
                        rs.getInt("idCriptoAtivo"),
                        rs.getString("nome"),
                        rs.getString("simbolo"),
                        rs.getDouble("precoAtual")
                );
                cripto.setQuantidade(rs.getDouble("quantidade"));
                criptoativos.add(cripto);
            }
        }

        return criptoativos;
    }

    /**
     * Atualiza a quantidade diretamente
     */
    public void atualizarQuantidade(int idCarteira, int idCriptoativo, double novaQuantidade) throws SQLException {
        String sql = "UPDATE Carteira_CriptoAtivo SET quantidade = ? WHERE idCarteira = ? AND idCriptoAtivo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, novaQuantidade);
            stm.setInt(2, idCarteira);
            stm.setInt(3, idCriptoativo);
            stm.executeUpdate();
        }
    }

    /**
     * Remove um criptoativo da carteira
     */
    public void removerCriptoativoDaCarteira(int idCarteira, int idCriptoativo) throws SQLException {
        String sql = "DELETE FROM Carteira_CriptoAtivo WHERE idCarteira = ? AND idCriptoAtivo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCarteira);
            stm.setInt(2, idCriptoativo);
            stm.executeUpdate();
        }
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }
}
