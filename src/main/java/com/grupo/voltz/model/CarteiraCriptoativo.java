package com.grupo.voltz.model;

public class CarteiraCriptoativo {

    private int idCarteira;
    private int idCriptoativo;
    private double quantidade;

    public CarteiraCriptoativo(int idCarteira, int idCriptoativo, double quantidade) {
        this.idCarteira = idCarteira;
        this.idCriptoativo = idCriptoativo;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public int getIdCarteira() { return idCarteira; }
    public int getIdCriptoativo() { return idCriptoativo; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
}
