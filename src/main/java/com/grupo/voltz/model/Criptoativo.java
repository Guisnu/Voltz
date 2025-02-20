package com.grupo.voltz.model;

public class Criptoativo {

    private static int ordem = Ordem.getIdOrdem();
    private String nome;
    private String simbolo;
    private double precoAtual;
    private double quantidade = 0.0;

    public Criptoativo(String nome, String simbolo, double precoAtual) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
    }

    public String getNome() {
        return nome;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public double getPrecoAtual() {
        return precoAtual;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getSaldo() {
        return quantidade * precoAtual;
    }

    public void atualizarPreco(double novoPreco) {
        this.precoAtual = novoPreco;
    }

    public void setQuantidade(double valor) {
        this.quantidade = valor / this.precoAtual;
    }
}
