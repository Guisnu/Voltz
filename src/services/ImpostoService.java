package services;

public class ImpostoService {

    public static double calcularImpostoSobreVenda(double valorVenda) {
        double taxaVenda = 0.02; // 2% de taxa de venda
        return valorVenda * taxaVenda;
    }
}
