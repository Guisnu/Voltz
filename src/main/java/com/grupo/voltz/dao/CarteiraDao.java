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

    /**
     * Insere uma nova carteira no banco de dados.
     * Assume a existência de uma sequence seq_carteira para geração de idCarteira.
     */
    public void cadastrar(Conta conta, Carteira carteira) throws SQLException {
        String sql = "INSERT INTO Carteira (idCarteira, idConta, nome, criptoativos, saldo) VALUES (seq_carteira.nextval, ?, ?, ?, ?)";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setLong(1, conta.getIdConta());
            stm.setString(2, carteira.getNome());
            stm.setInt(3, carteira.getCriptoativos().size());
            stm.setDouble(4, carteira.getSaldo());
            stm.executeUpdate();
        }
    }

    /**
     * Busca uma carteira pelo seu identificador.
     */
    public Carteira buscarPorId(Long idCarteira) throws SQLException {
        String sql = "SELECT * FROM Carteira WHERE idCarteira = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setLong(1, idCarteira);
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return parseCarteira(rs);
                }
            }
        }
        return null;
    }

    /**
     * Lista todas as carteiras cadastradas.
     */
    public List<Carteira> listar() throws SQLException {
        String sql = "SELECT * FROM Carteira";
        List<Carteira> lista = new ArrayList<>();
        try (PreparedStatement stm = conexao.prepareStatement(sql);
             ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                lista.add(parseCarteira(rs));
            }
        }
        return lista;
    }

    /**
     * Lista todas as carteiras pertencentes a uma conta específica.
     */
    public List<Carteira> listarPorConta(Long idConta) throws SQLException {
        String sql = "SELECT * FROM Carteira WHERE idConta = ?";
        List<Carteira> lista = new ArrayList<>();
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setLong(1, idConta);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    lista.add(parseCarteira(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Atualiza o nome de uma carteira.
     */
    public void atualizarNome(Long idCarteira, String novoNome) throws SQLException {
        String sql = "UPDATE Carteira SET nome = ? WHERE idCarteira = ?";
        try (PreparedStatement stm = conexao.prepareStatement(sql)) {
            stm.setString(1, novoNome);
            stm.setLong(2, idCarteira);
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

    /**
     * Converte um registro de ResultSet em um objeto Carteira.
     * Observação: popula apenas nome, saldo e conta mínima.
     */
    private Carteira parseCarteira(ResultSet rs) throws SQLException {
        Integer idConta = rs.getInt("idConta");
        String nome = rs.getString("nome");
        int criptoCount = rs.getInt("criptoativos");
        double saldo = rs.getDouble("saldo");

        // Cria objeto Conta mínimo apenas com o id
        Conta conta = new Conta(idConta, null, null, null, 0.0, new ArrayList<>(), 0);
        Carteira carteira = new Carteira(nome, conta);
        carteira.setSaldo(saldo);
        // Opcional: preencher lista de criptoativos com placeholders, se necessário
        return carteira;
    }
}
