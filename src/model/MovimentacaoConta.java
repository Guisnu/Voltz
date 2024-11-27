package model;

import enums.TipoMovimentacaoEnum;
import java.time.LocalDateTime;

public class MovimentacaoConta {

    private Conta conta;
    private TipoMovimentacaoEnum tipo;
    private double valor;
    private LocalDateTime data = LocalDateTime.now();

    public MovimentacaoConta(Conta conta, TipoMovimentacaoEnum tipo, double valor) {
        this.conta = conta;
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDateTime.now();
    }

    public Conta getInvestidor() {
        return conta;
    }

    public TipoMovimentacaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimentacaoEnum tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return data;
    }

//    // Método para exibir informações da movimentação (para fins de log ou auditoria)
//    @Override
//    public String toString() {
//        return "Movimentacao{" +
//                ", Conta=" + conta.getNome() +
//                ", empresa=" + (empresa != null ? empresa.getRazaoSocial() : "N/A") +
//                ", tipo='" + tipo + '\'' +
//                ", valor=" + valor +
//                ", data=" + data +
//                '}';
//    }
}
