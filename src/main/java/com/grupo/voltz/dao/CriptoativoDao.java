package com.grupo.voltz.dao;

import com.grupo.voltz.model.Criptoativo;
import com.grupo.voltz.services.ConnectionService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CriptoativoDao {

    private final Connection conexao;

    public CriptoativoDao() throws SQLException {
        this.conexao = ConnectionService.getConnection();
    }

    public void fecharConexao() throws SQLException {
        if (conexao != null && !conexao.isClosed()) {
            conexao.close();
        }
    }

    /**
     * Insere um novo Criptoativo na base
     */
    public void cadastrar(Criptoativo cripto) throws SQLException {
        String sql = "INSERT INTO Criptoativo (idCriptoativo, nome, simbolo, precoAtual) " +
                "VALUES (seq_criptoativo.nextval, ?, ?, ?)";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, cripto.getNome());
            stm.setString(2, cripto.getSimbolo());
            stm.setDouble(3, cripto.getPrecoAtual());
            stm.executeUpdate();
        }
    }

    /**
     * Verifica existência por símbolo
     */
    public boolean existePorSimbolo(String simbolo) throws SQLException {
        String sql = "SELECT 1 FROM Criptoativo WHERE simbolo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, simbolo);
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Atualiza preço de um criptoativo existente
     */
    public void atualizarPreco(int idCripto, double novoPreco) throws SQLException {
        String sql = "UPDATE Criptoativo SET precoAtual = ? WHERE idCriptoativo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setDouble(1, novoPreco);
            stm.setInt(2, idCripto);
            stm.executeUpdate();
        }
    }

    /**
     * Remove um criptoativo pelo ID
     */
    public void deletar(int idCripto) throws SQLException {
        String sql = "DELETE FROM Criptoativo WHERE idCriptoativo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCripto);
            stm.executeUpdate();
        }
    }

    private Criptoativo parseCriptoativo(ResultSet rs) throws SQLException {
        int id = rs.getInt("idCriptoativo");
        String nome = rs.getString("nome");
        String simbolo = rs.getString("simbolo");
        double precoAtual = rs.getDouble("precoAtual");

        Criptoativo cripto = new Criptoativo(nome, simbolo, precoAtual);
        cripto.setId(id);
        return cripto;
    }

    /**
     * Busca um criptoativo pelo ID
     */
    public Criptoativo buscarPorId(int idCripto) throws SQLException {
        String sql = "SELECT * FROM Criptoativo WHERE idCriptoativo = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setInt(1, idCripto);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return parseCriptoativo(rs);
                }
            }
        }
        return null;
    }

    /**
     * Lista todos os criptoativos cadastrados
     */
    public List<Criptoativo> listarTodos() throws SQLException {
        String sql = "SELECT * FROM Criptoativo";
        List<Criptoativo> lista = new ArrayList<>();
        try (PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                lista.add(parseCriptoativo(rs));
            }
        }
        return lista;
    }
}

