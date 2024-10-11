package model;

import enums.TipoMovimentacaoEnum;

import java.util.ArrayList;
import java.util.List;

public class Investidor {

    private String nome;
    private String email;
    private String senha;
    private double saldo = 0.0;
    private List<Empresa> empresas = new ArrayList<Empresa>();
    private List<Carteira> carteiras = new ArrayList<Carteira>();
    private List<MovimentacaoConta> movimentacoes = new ArrayList<MovimentacaoConta>();
    private List<Transacao> transacoes = new ArrayList<Transacao>();

    public Investidor(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

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
        System.out.println("===MOVIMENTAÇÕES NA CONTA===");

        for (MovimentacaoConta movimentacao : movimentacoes) {
            System.out.println("Tipo: " + movimentacao.getTipo() + ", Valor: " + movimentacao.getValor());
        }

        return movimentacoes;
    }

    public boolean depositar(double valor) {
        if (valor < 0){
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

    // Método para exibir informações do investidor (para fins de log ou auditoria)
    @Override
    public String toString() {
        return "Investidor={" +
                "nome=" + nome +
                ", email=" + email +
                ", senha='" + senha + '\'' +
                ", saldo=" + saldo +
                '}';
    }

}

