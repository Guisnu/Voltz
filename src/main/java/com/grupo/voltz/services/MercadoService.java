package com.grupo.voltz.services;

import com.grupo.voltz.dao.CriptoativoDao;
import com.grupo.voltz.model.Criptoativo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MercadoService extends BaseService {

    private List<Criptoativo> criptoativos = new ArrayList<>();

//    public List<Criptoativo> getCriptoativos() {
//        // Simulando fetch dados de uma API de criptoativos
//        criptoativos.add(new Criptoativo("Bitcoin", "BTC", 336594.63));
//        criptoativos.add(new Criptoativo("Ethereum", "ETH", 13608.43));
//        criptoativos.add(new Criptoativo("Tether USDt", "USDT", 5.57));
//        criptoativos.add(new Criptoativo("BNB", "BNB", 3099.42));
//
//        return criptoativos;
//    }
    @Override
    public List<Criptoativo> executar() {
        // Simulando fetch dados de uma API de criptoativos
        criptoativos.add(new Criptoativo("Bitcoin", "BTC", 336594.63));
        criptoativos.add(new Criptoativo("Ethereum", "ETH", 13608.43));
        criptoativos.add(new Criptoativo("Tether USDt", "USDT", 5.57));
        criptoativos.add(new Criptoativo("BNB", "BNB", 3099.42));

        try {
            CriptoativoDao criptoDao = new CriptoativoDao();

            for (Criptoativo c : criptoativos) {
                // Verifica se já existe pelo símbolo antes de cadastrar
                if (!criptoDao.existePorSimbolo(c.getSimbolo())) {
                    criptoDao.cadastrar(c);
                }
            }

            criptoDao.fecharConexao(); // Boa prática!
        } catch (SQLException e) {
            System.out.println("Erro ao popular o banco: " + e.getMessage());
        }

        return criptoativos;
    }

}
