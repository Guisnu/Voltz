package com.grupo.voltz.model;

import java.util.ArrayList;
import java.util.List;

public class Carteira {

    Integer idCarteira;
    private String nome;
    private Conta conta;
    private double saldo;
    private List<Criptoativo> criptoativos = new ArrayList<>();
    private List<Transacao> transacoes = new ArrayList<>();
    private List<Ordem> ordens = new ArrayList<>();
    private double saldoResv = 0.0;

    public Carteira(){}

    public Carteira(Integer idCarteira, String nome) {
        this.idCarteira = idCarteira;
        this.nome = nome;
    }
    public Carteira( String nome) {
        this.nome = nome;
    }

    public Integer getIdCarteira() {return idCarteira;}

    public void setIdCarteira(Integer idCarteira) {this.idCarteira = idCarteira;}

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getSaldoResv() {
        return saldoResv;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setSaldoResv(double saldoResv) {
        if (saldoResv > this.saldo) {
            throw new IllegalArgumentException("A quantidade da reserva é maior que a quantidade disponível na carteira!");
        }

        this.saldoResv = saldoResv;
    }

    //trocado if por while?
    public void depositar(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor deve ser maior que Zero!");
        }

        double saldoConta = this.conta.getSaldo();

        if (saldoConta < valor) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }

        this.conta.setSaldo(saldoConta - valor);
        this.saldo = valor;
    }

    public void adicionarCriptoativo(Criptoativo criptoativo) {
        criptoativos.add(criptoativo);
    }

    public List<Criptoativo> getCriptoativos() {
        return criptoativos;
    }

    public void removerCriptoativo(Criptoativo criptoativo) {
        criptoativos.remove(criptoativo);
    }

    public void adicionarTransacao(Transacao transacao) {
        transacoes.add(transacao);
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void adicionarOrdens(Ordem ordem) {
        ordens.add(ordem);
    }

    public List<Ordem> getOrdens() {
        return ordens;
    }

    public double calcularValorTotal() {
        //Banco de dados
        double total = 0.0;

        for (Criptoativo criptoativo : criptoativos) {
            total += criptoativo.getSaldo();
        }

        return total;
    }

    // Método para exibir informações da carteira (para fins de log ou auditoria)
    // @Override
    // public String toString() {
    //     return "Carteira={" +
    //             "nome=" + nome +
    //             ", investidor=" + conta.getNome() +
    //             ", saldo=" + saldo +
    //             '}';
    // }
}
