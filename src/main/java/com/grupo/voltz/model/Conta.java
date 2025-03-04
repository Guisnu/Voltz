package com.grupo.voltz.model;

import com.grupo.voltz.enums.TipoMovimentacaoEnum;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private static long idConta;
    private Double saldo = 0.0;
    private String nomeInvestidor;
    private String emailInvestidor;
    private String senhaInvestidor;
    private List<Empresa> empresas = new ArrayList<>();
    private List<Carteira> carteiras = new ArrayList<>();
    private List<MovimentacaoConta> movimentacoes = new ArrayList<>();
    private List<Transacao> transacoes = new ArrayList<>();

    public Conta(Long id, String nomeInvestidor, String emailInvestidor, String senhaInvestidor, Double saldo, List<Carteira> carteiras, int movimentacoes) {
        this.idConta = id;
        this.nomeInvestidor = nomeInvestidor;
        this.emailInvestidor = emailInvestidor;
        this.senhaInvestidor = senhaInvestidor;
        this.saldo = saldo;

        this.carteiras = new ArrayList<>(carteiras);
        this.movimentacoes = new ArrayList<>(movimentacoes);
    }

    public Conta(String nomeInvestidor, String emailInvestidor, String senhaInvestidor){
        this.nomeInvestidor = nomeInvestidor;
        this.emailInvestidor = emailInvestidor;
        this.senhaInvestidor = senhaInvestidor;
    }
    public Conta(String emailInvestidor, String senhaInvestidor){
        this.emailInvestidor = emailInvestidor;
        this.senhaInvestidor = senhaInvestidor;
    }


    public static long getIdConta() {
        return idConta;
    }

    public static void setIdConta(long idConta) {
        Conta.idConta = idConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public String getNomeInvestidor() {return nomeInvestidor;}

    public void setNomeInvestidor(String nomeInvestidor) {this.nomeInvestidor = nomeInvestidor;}

    public String getEmailInvestidor() {return emailInvestidor;}

    public void setEmailInvestidor(String emailInvestidor) {this.emailInvestidor = emailInvestidor;}

    public String getSenhaInvestidor() {return senhaInvestidor;}

    public void setSenhaInvestidor(String senhaInvestidor) {this.senhaInvestidor = senhaInvestidor;}

    public void adicionarCarteira(Carteira carteira) {
        carteiras.add(carteira);
    }

    public void removerCarteira(Carteira carteira) {
        carteiras.remove(carteira);
    }

    public void adicionarEmpresa(Empresa empresa) {
        empresas.add(empresa);
    }

    public void removerEmpresa(Empresa empresa) {
        empresas.remove(empresa);
    }

    public List<Empresa> getEmpresas() {
        System.out.println("===EMPRESAS===");

        for (Empresa empresa : empresas) {
            System.out.println("Razão Social: " + empresa.getRazaoSocial() + ", CNPJ: " + empresa.getCnpj());
        }

        return empresas;
    }

    public void adicionarMovimentacao(MovimentacaoConta movimentacaoConta) {
        movimentacoes.add(movimentacaoConta);
    }

    public List<MovimentacaoConta> getMovimentacoes() {
        for (MovimentacaoConta movimentacao : movimentacoes) {
            System.out.println("Tipo: " + movimentacao.getTipo() + ", Valor: " + movimentacao.getValor());
        }

        return movimentacoes;
    }

    public boolean depositar(double valor) {
        if (valor < 0) {
            System.out.println("O valor deve ser maior que Zero!");
            return false;
        }
        adicionarMovimentacao(new MovimentacaoConta(this, TipoMovimentacaoEnum.DEPOSITO, valor));

        this.saldo += valor;
        return true;

    }

    public boolean sacar(double valor) {
        if (this.saldo >= valor && this.saldo > 0) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public List<Transacao> visualizarHistorico() {
        List<Transacao> historico = new ArrayList<>();
        for (Transacao transacao : transacoes) {
            historico.add(transacao);
        }
        return historico;
    }

    // @Override
    // public String toString() {
    //     return "Conta={" +
    //             "id=" + id + //nao precisa do get pois ja fiz la em cima ED
    //             "nome=" + getNome() +
    //             ", email=" + getEmail() +
    //             ", senha='" + getSenha() + '\'' +
    //             ", saldo=" + saldo +
    //             '}';
    // }
}
