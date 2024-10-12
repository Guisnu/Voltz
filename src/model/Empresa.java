package model;

public class Empresa {

    private Conta conta;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;
    private double saldo;

    public Empresa(Conta conta, String razaoSocial, String nomeFantasia, String cnpj, double saldo) {
        this.conta = conta;
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

    public Conta getInvestidor() {
        return conta;
    }

    public void depositar(double valor) {
        //Banco de dados
        this.saldo += valor;
    }

    public boolean transferirParaSaldoInvestidor(double valor) {
        this.saldo -= valor;
        this.conta.depositar(valor);
        return true;
    }
}
