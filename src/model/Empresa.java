package model;

public class Empresa {

    private Investidor investidor;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private double saldo;

    public Empresa(Investidor investidor, String razaoSocial, String nomeFantasia, String cnpj, double saldo) {
        this.investidor = investidor;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.saldo = saldo;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public double getSaldo() {
        return saldo;
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public void depositar(double valor) {
        //Banco de dados
        this.saldo += valor;
    }

    public boolean transferirParaSaldoInvestidor(double valor) {
        this.saldo -= valor;
        this.investidor.depositar(valor);
        return true;
    }
}
