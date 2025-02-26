package com.grupo.voltz.model;

import com.grupo.voltz.enums.OrdemStatusEnum;
import com.grupo.voltz.enums.TipoOrdemEnum;
import com.grupo.voltz.enums.TipoTransacaoEnum;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ordem {

    private static int idOrdem;
    private String codigo;
    private OrdemStatusEnum status = OrdemStatusEnum.EMABERTO;
    private TipoOrdemEnum tipo;
    private Criptoativo criptoativoOrdem;
    private Carteira carteiraOrdem;
    private double precoLimite;
    private double quantidade;
    private LocalDateTime dataOrdem = LocalDateTime.now();

    public Ordem(TipoOrdemEnum tipo, Carteira carteiraOrdem, Criptoativo criptoativoOrdem, double precoLimite) {
        this.tipo = tipo;
        this.criptoativoOrdem = criptoativoOrdem;
        this.carteiraOrdem = carteiraOrdem;

        setPrecoLimite(precoLimite);
        setQuantidade();
        setCodigo();
    }

    public String getCodigo() {
        return codigo;
    }

    public OrdemStatusEnum getStatus() {
        return status;
    }

    public double getPrecoLimite() {
        return precoLimite;
    }

    public TipoOrdemEnum getTipo() {
        return tipo;
    }

    public Criptoativo getCriptoativo() {
        return criptoativoOrdem;
    }

    public Carteira getCarteira() {
        return carteiraOrdem;
    }

    public LocalDateTime getData() {
        return dataOrdem;
    }

    private void setCodigo() {
        List<Ordem> ordens = carteiraOrdem.getOrdens();

        if (ordens.isEmpty()) {
            this.codigo = "ORD1";
            return;
        }

        Ordem ultimaOrdem = ordens.get(ordens.size() - 1);
        String ultimoCodigo = ultimaOrdem.getCodigo();

        int ultimoNumero = Integer.parseInt(ultimoCodigo.replace("ORD", ""));

        int novoNumero = ultimoNumero + 1;

        this.codigo = "ORD" + novoNumero;
    }

    public void setPrecoLimite(double precoLimite) {
        if (precoLimite > carteiraOrdem.getSaldo()) {
            throw new IllegalArgumentException("Saldo insuficiente na carteira.");
        }
        this.precoLimite = precoLimite;
    }

    private void setQuantidade() {
        this.quantidade = criptoativoOrdem.getQuantidade();
    }

    public void adicionarOrdem() {
        carteiraOrdem.adicionarOrdens(this);
    }

    /***
     Envia a ordem para executar no orgão competente(corretora, etc)
     ***/
    public void enviarOrdem() {
        this.getOrdemCarteira();
        this.status = OrdemStatusEnum.EMANDAMENTO;

        Transacao transacao = new Transacao(TipoTransacaoEnum.RESERVA, this.criptoativoOrdem, this.precoLimite);
        transacao.executarTransacao(this.carteiraOrdem);
    }

    public void executarOrdem() {
        if (this.status != OrdemStatusEnum.EMANDAMENTO) {
            throw new IllegalArgumentException("Não é possível executar essa ordem!");
        }

        String tipoAux = String.valueOf(this.tipo);
        TipoTransacaoEnum aux = tipoAux.equals(String.valueOf(TipoTransacaoEnum.COMPRA))
                ? TipoTransacaoEnum.COMPRA : TipoTransacaoEnum.VENDA;

        this.status = OrdemStatusEnum.FINALIZADA;

        carteiraOrdem.setSaldo(carteiraOrdem.getSaldo() + this.precoLimite);
        carteiraOrdem.setSaldoResv(carteiraOrdem.getSaldoResv() - this.precoLimite);

        Transacao transacaoOrdem = new Transacao(aux, this.criptoativoOrdem, this.precoLimite);
        transacaoOrdem.executarTransacao(carteiraOrdem);
    }

    public void cancelarOrdem() {

        if (this.status != OrdemStatusEnum.EMABERTO) {
            throw new IllegalArgumentException("Não é possível cancelar essa ordem!");
        }

        this.getOrdemCarteira();

        this.status = OrdemStatusEnum.CANCELADA;

        carteiraOrdem.setSaldo(carteiraOrdem.getSaldo() + this.precoLimite);
        carteiraOrdem.setSaldoResv(carteiraOrdem.getSaldoResv() - this.precoLimite);
    }

    private Ordem getOrdemCarteira() {
        Ordem ordemCarteira = carteiraOrdem.getOrdens().stream().filter(
                ordem -> ordem.codigo.equals(this.codigo)
        ).findFirst().orElse(null);

        if (ordemCarteira == null) {
            throw new IllegalArgumentException("Ordem não encontrada na carteira selecionada!");
        }

        return ordemCarteira;
    }

    public String toString() {
        return "Ordem={"
                + "codigo=" + codigo
                + ", status=" + status
                + ", tipo=" + tipo
                + ", criptoativo=" + criptoativoOrdem.getSimbolo()
                + ", preço limite=" + precoLimite
                + ", data=" + dataOrdem.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                + '}';
    }
}
