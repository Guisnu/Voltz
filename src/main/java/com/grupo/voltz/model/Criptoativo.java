package com.grupo.voltz.model;

public class Criptoativo {

    private Integer idCriptoativo;
    private String nome;
    private String simbolo;
    private double precoAtual;
    private double quantidade = 0;

    public Criptoativo(String nome, String simbolo, double precoAtual) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
    }

    public Criptoativo( Integer idCriptoativo, String nome, String simbolo, double precoAtual) {
        this.idCriptoativo = idCriptoativo;
        this.nome = nome;
        this.simbolo = simbolo;
        this.precoAtual = precoAtual;
    }

    public int getIdCriptoativo() {
        return idCriptoativo;
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
    public double getQuantidadeCalculo() {
        System.out.println("DEBUG - Getter da quantidade: " + this.quantidade);
        return quantidade/ this.precoAtual;
    }

    public double getSaldo() {
        return quantidade * precoAtual;
    }

    public void atualizarPreco(double novoPreco) {
        this.precoAtual = novoPreco;
    }

    public void setQuantidade(double valor) {
        this.quantidade = valor;
    }

}
