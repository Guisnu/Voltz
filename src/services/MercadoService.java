package services;

import model.Criptoativo;

import java.util.ArrayList;
import java.util.List;

public class MercadoService extends BaseService{

    private List<Criptoativo> criptoativos = new ArrayList<>();

    public MercadoService() {
        //contrutor vazio
    }

    public List<Criptoativo> getCriptoativos() {
        // Simulando fetch dados de uma API de criptoativos
        criptoativos.add(new Criptoativo("Bitcoin", "BTC", 336594.63));
        criptoativos.add(new Criptoativo("Ethereum", "ETH", 13608.43));
        criptoativos.add(new Criptoativo("Tether USDt", "USDT", 5.57));
        criptoativos.add(new Criptoativo("BNB", "BNB", 3099.42));

        return criptoativos;
    }

}
