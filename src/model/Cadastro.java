package model;

import java.util.Date;

public class Cadastro {

    private static int contador = 0;
    private int id;
    private Investidor investidor;
    private Empresa empresa;
    private Date dataCadastro;

    public Cadastro(Investidor investidor, Empresa empresa) {
        this.id = ++contador;
        this.investidor = investidor;
        this.empresa = empresa;
        this.dataCadastro = new Date();
    }

}
