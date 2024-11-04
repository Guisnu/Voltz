package model;

import java.util.Date;

public class Cadastro {
    private static int idCadastro = Investidor.getId();
    private static int idEmpresa = Empresa.getIdEmpresa();
    private Date dataCadastro;

    public Cadastro() {
        this.dataCadastro = new Date();
    }

}
