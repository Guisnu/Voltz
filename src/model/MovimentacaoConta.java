package model;

import enums.TipoMovimentacaoEnum;

import java.time.LocalDateTime;

public class MovimentacaoConta {

    private Investidor investidor;
    private Empresa empresa;
    private TipoMovimentacaoEnum tipo;
    private double valor;
    private LocalDateTime data = LocalDateTime.now();

    public MovimentacaoConta(Investidor investidor, Empresa empresa, TipoMovimentacaoEnum tipo, double valor) {
        this.investidor = investidor;
        this.empresa = empresa;
        this.tipo = tipo;
        this.valor = valor;
    }

    public MovimentacaoConta(Investidor investidor, TipoMovimentacaoEnum tipo, double valor) {
        this.investidor = investidor;
        this.tipo = tipo;
        this.valor = valor;
        this.data = LocalDateTime.now();
    }

    public Investidor getInvestidor() {
        return investidor;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

    // Método para exibir informações da movimentação (para fins de log ou auditoria)
    @Override
    public String toString() {
        return "Movimentacao{" +
                ", investidor=" + investidor.getNome() +
                ", empresa=" + (empresa != null ? empresa.getRazaoSocial() : "N/A") +
                ", tipo='" + tipo + '\'' +
                ", valor=" + valor +
                ", data=" + data +
                '}';
    }
}
