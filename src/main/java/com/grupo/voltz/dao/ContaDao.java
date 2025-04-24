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

public class ContaDao {

    private final Connection conexao;

    public ContaDao() throws SQLException{
        conexao = ConnectionService.getConnection();
    }
    public void fecharConexao() throws SQLException {
        conexao.close();
    }

    public void cadastrar(Conta conta) throws SQLException {
        PreparedStatement stm = conexao.prepareStatement("INSERT INTO Conta (nomeinvestidor, emailInvestidor, senhaInvestidor, saldo, carteiras ) VALUES (?,?,?,?,?)");
        stm.setString(1, conta.getNomeInvestidor());
        stm.setString(2, conta.getEmailInvestidor());
        stm.setString(3, conta.getSenhaInvestidor());
        stm.setDouble(4, conta.getSaldo());
        stm.setInt(5, conta.getCarteiras().size());
        stm.executeUpdate();
    }

    private Conta parseConta(ResultSet result) throws SQLException {
        Integer id = result.getInt("id_conta");
        String nome = result.getString("nomeInvestidor");
        String email = result.getString("emailInvestidor");
        String senha = result.getString("senhaInvestidor");
        double saldo = result.getDouble("saldo");

        return new Conta(id, nome, email, senha, saldo, new ArrayList<>());
    }

    public List<Conta> listar()throws SQLException{
        PreparedStatement stm = conexao.prepareStatement("SELECT * FROM Conta");
        ResultSet result = stm.executeQuery();
        List<Conta> lista = new ArrayList<>();
        while (result.next()){
            lista.add(parseConta(result));
        }
        return lista;
    }

    public Conta login(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM Conta WHERE emailInvestidor = ? AND senhaInvestidor = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, email);
        stm.setString(2, senha);
        ResultSet result = stm.executeQuery();

        if (result.next()) {
            Integer id = result.getInt("id_conta");
            String nome = result.getString("nomeInvestidor");
            double saldo = result.getDouble("saldo");

            Conta conta = new Conta(id, nome, email, senha, saldo, new ArrayList<>());

            return conta;
        }
        return null;
    }

    public void atualizarNomeInvestidor(Integer idConta, String novoNome) throws SQLException {
        String sql = "UPDATE Conta SET nomeInvestidor = ? WHERE id_conta = ?";
        PreparedStatement stm = conexao.prepareStatement(sql);
        stm.setString(1, novoNome);
        stm.setInt(2, idConta);
        stm.executeUpdate();
    }

    public void deletarConta(Integer idConta) throws SQLException {
        try {
            conexao.setAutoCommit(false);

            String sqlBuscaCarteiras = "SELECT idCarteira FROM Carteira WHERE idConta = ?";
            PreparedStatement buscarCarteiras = conexao.prepareStatement(sqlBuscaCarteiras);
            buscarCarteiras.setInt(1, idConta);
            ResultSet rsCarteiras = buscarCarteiras.executeQuery();

            List<Integer> idsCarteiras = new ArrayList<>();
            while (rsCarteiras.next()) {
                idsCarteiras.add(rsCarteiras.getInt("idCarteira"));
            }

            // 2. Apagar criptoativos das carteiras
            for (Integer idCarteira : idsCarteiras) {
                PreparedStatement delCriptoativos = conexao.prepareStatement("DELETE FROM Carteira_Criptoativo WHERE idCarteira = ?");
                delCriptoativos.setInt(1, idCarteira);
                delCriptoativos.executeUpdate();
            }

            // 3. Apagar transações da conta
            PreparedStatement delTransacoes = conexao.prepareStatement("DELETE FROM Transacao WHERE idConta = ?");
            delTransacoes.setInt(1, idConta);
            delTransacoes.executeUpdate();

            // 4. Apagar carteiras
            PreparedStatement delCarteiras = conexao.prepareStatement("DELETE FROM Carteira WHERE idConta = ?");
            delCarteiras.setInt(1, idConta);
            delCarteiras.executeUpdate();

            // 5. Apagar a conta
            PreparedStatement delConta = conexao.prepareStatement("DELETE FROM Conta WHERE id_conta = ?");
            delConta.setInt(1, idConta);
            delConta.executeUpdate();

            conexao.commit();
        } catch (SQLException e) {
            conexao.rollback();
            System.out.println("Erro ao excluir conta: " + e.getMessage());
        } finally {
            conexao.setAutoCommit(true);
        }
    }


    public void atualizarSaldo(int idConta, double novoSaldo) throws SQLException {
        String sql = "UPDATE Conta SET saldo = ? WHERE id_conta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setDouble(1, novoSaldo);
            stmt.setInt(2, idConta);
            stmt.executeUpdate();
        }
    }

    public void atualizarQuantidadeCarteiras(int idConta, int novaQuantidade) throws SQLException {
        String sql = "UPDATE Conta SET carteiras = ? WHERE id_conta = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, novaQuantidade);
            stmt.setInt(2, idConta);
            stmt.executeUpdate();
        }
    }

}
