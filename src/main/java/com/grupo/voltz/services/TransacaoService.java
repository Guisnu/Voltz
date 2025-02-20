package com.grupo.voltz.services;

import com.grupo.voltz.model.Carteira;
import com.grupo.voltz.model.Criptoativo;
import com.grupo.voltz.model.Transacao;

public class TransacaoService extends BaseService{

    private Transacao transacao;
    private Carteira carteira;

    public TransacaoService(Transacao transacao, Carteira carteira) {
        this.transacao = transacao;
        this.carteira = carteira;
    }

    public void handle() {
        carteira.adicionarTransacao(this.transacao);

        switch (this.transacao.getTipo()) {
            case COMPRA -> compra();
            case VENDA -> venda();
            case RESERVA -> reserva();
            default -> throw new IllegalArgumentException("Transação indefinida!");
        }
    }

    private void compra() {
        System.out.println("COMPRANDO " + transacao.getCriptoativo().getSimbolo() + "...");
        double valorTransacao = transacao.getValor();

        if (carteira.getSaldo() < valorTransacao) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }

        carteira.setSaldo(carteira.getSaldo() - valorTransacao);
        Criptoativo criptoativo = transacao.getCriptoativo();

        if (carteira.getCriptoativos().contains(criptoativo)) {
            criptoativo.setQuantidade(criptoativo.getSaldo() + valorTransacao);
        } else {
            criptoativo.setQuantidade(valorTransacao);
            carteira.adicionarCriptoativo(criptoativo);
        }

        System.out.println("COMPRA CONCLUÍDA!");
    }

    private void venda() {
        System.out.println("VENDENDO " + transacao.getCriptoativo().getSimbolo() + "...");
        Criptoativo criptoativoTransacao = transacao.getCriptoativo();

        Criptoativo criptoativoCarteira = carteira.getCriptoativos().stream()
                .filter(crip -> crip.getSimbolo().equals(criptoativoTransacao.getSimbolo()))
                .findFirst().orElse(null);

        if (criptoativoCarteira == null) {
            throw new IllegalArgumentException("Criptoativo não encontrado!");
        }

        if (transacao.getValor() > criptoativoCarteira.getSaldo()) {
            throw new IllegalArgumentException("Saldo do criptoativo insuficiente!");
        }

        ImpostoSobreVenda impostoSobreVenda = new ImpostoSobreVenda();
        double resultadoSobreVenda = impostoSobreVenda.executar(transacao.getValor());
        criptoativoCarteira.setQuantidade(criptoativoCarteira.getSaldo() - (transacao.getValor() + resultadoSobreVenda));
        carteira.setSaldo(carteira.getSaldo() + (transacao.getValor() - resultadoSobreVenda));

        System.out.println("VENDA CONCLUÍDA!");
    }

    private void reserva() {
        System.out.println("RESERVANDO " + transacao.getCriptoativo().getSimbolo() + "...");
        carteira.setSaldoResv(transacao.getValor());
        carteira.setSaldo(carteira.getSaldo() - transacao.getValor());
        System.out.println("RESERVA CONCLUÍDA!");
    }

}
