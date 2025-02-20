package com.grupo.voltz.model;

public class Empresa {

    private static int idEmpresa = 0;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;

    public Empresa(String razaoSocial, String nomeFantasia, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public String getCnpj() {
        return cnpj;
    }

    public static int getIdEmpresa() {
        return ++idEmpresa;
    }

}
