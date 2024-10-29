package services;

public class ImpostoSobreVenda extends BaseService{

    public static double calcularImpostoSobreVenda(double valorVenda) {
        double taxaVenda = 0.02; // Taxa padrão de 2%
        return valorVenda * taxaVenda;
    }

    @Override
    public void executar() {
        // Podemos transferir a execução do calculo de imposto aqui
    }
}
