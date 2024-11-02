package services;

public class ImpostoSobreVenda extends BaseService{

//    public static double calcularImpostoSobreVenda(double valorVenda) {
//        double taxaVenda = 0.02; // Taxa padrão de 2%
//        return valorVenda * taxaVenda;
//    }

    @Override
    public Object executar(Object param) {
        double taxaVenda = 0.02; // Taxa padrão de 2%
        return (double)param * taxaVenda;
    }
}
