package com.grupo.voltz.services;

public class ImpostoSobreVenda extends BaseService {

//    public static double calcularImpostoSobreVenda(double valorVenda) {
//        double taxaVenda = 0.02; // Taxa padrão de 2%
//        return valorVenda * taxaVenda;
//    }
    @Override
    public double executar(Object param) {
        double taxaVenda = 0.02; // Taxa padrão de 2%
        double resultadoTaxaVenda = (double) param * taxaVenda;
        return resultadoTaxaVenda;
    }
}
