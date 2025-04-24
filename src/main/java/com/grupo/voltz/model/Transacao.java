package com.grupo.voltz.model;

import com.grupo.voltz.enums.TipoTransacaoEnum;
import com.grupo.voltz.services.TransacaoService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Transacao {

    private Integer IdTransacao;
    private TipoTransacaoEnum tipo;
    private Criptoativo criptoativo;
    private double quantidade;
    private double valor;
    private LocalDate data = LocalDate.now();

    public Transacao(TipoTransacaoEnum tipo, Criptoativo criptoativo, Double quantidade, double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("O valor deve ser maior que Zero!");
        }
        this.tipo = tipo;
        this.criptoativo = criptoativo;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public void setData(LocalDate localDate) {this.data = localDate;}

    public void setIdTransacao(int idTransacao) {this.IdTransacao = idTransacao;}

    public int getIdTransacao() {return IdTransacao;}

    public TipoTransacaoEnum getTipo() {
        return tipo;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Criptoativo getCriptoativo() {
        return criptoativo;
    }

}

