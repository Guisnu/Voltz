package services;

import model.Carteira;
import model.Criptoativo;
import model.Transacao;

import java.util.List;

import static services.ImpostoService.calcularImpostoSobreVenda;

public class TransacaoService {

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
        System.out.println("");
        System.out.println("COMPRANDO " + transacao.getCriptoativo().getSimbolo() + "...");
        double valorTransacao = transacao.getValor();

        if(carteira.getSaldo() < valorTransacao) {
            throw new IllegalArgumentException("Saldo insuficiente!");
        }

        this.carteira.setSaldo(this.carteira.getSaldo() - valorTransacao);
        Criptoativo criptoativo = transacao.getCriptoativo();

        System.out.println("COMPRA CONCLUÍDA!");

        if(carteira.getCriptoativos().contains(criptoativo)) {
            criptoativo.setQuantidade(criptoativo.getSaldo() + valorTransacao);
            return;
        }

        criptoativo.setQuantidade(valorTransacao);
        this.carteira.adicionarCriptoativo(criptoativo);
    }

    private void venda() {
        System.out.println("");
        System.out.println("VENDENDO " + transacao.getCriptoativo().getSimbolo() + "...");

        Criptoativo criptoativoTransacao = transacao.getCriptoativo();

        Criptoativo criptoativoCarteira = carteira.getCriptoativos().stream().filter(
                crip -> crip.getSimbolo() == criptoativoTransacao.getSimbolo()
        ).findFirst().orElse(null);

        if(criptoativoCarteira == null) {
            throw new IllegalArgumentException("Criptoativo não encontrado!");
        }

        if(this.transacao.getValor() > criptoativoCarteira.getSaldo()) {
            throw new IllegalArgumentException("Saldo do criptoativo insuficiente!");
        }

        double impostoSobreVenda = calcularImpostoSobreVenda(this.transacao.getValor());

        criptoativoCarteira.setQuantidade(criptoativoCarteira.getSaldo() - (this.transacao.getValor() + impostoSobreVenda));

        this.carteira.setSaldo(this.carteira.getSaldo() + (this.transacao.getValor() - impostoSobreVenda));

        System.out.println("VENDA CONCLUÍDA!");
    }

    private void reserva() {
        System.out.println("");
        System.out.println("RESERVANDO " + transacao.getCriptoativo().getSimbolo() + "...");

        this.carteira.setSaldoResv(this.transacao.getValor());
        this.carteira.setSaldo(this.carteira.getSaldo() - this.transacao.getValor());

        System.out.println("RESERVA CONCLUÍDA!");
    }
}
