package model;

public class Empresa {

    private int idEmpresa;
    private int contador;
    private String razaoSocial;
    private String nomeFantasia;
    private String cnpj;

    public Empresa(String razaoSocial, String nomeFantasia, String cnpj) {
        this.idEmpresa = ++contador;
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

}
