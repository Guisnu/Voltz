package model;

public class Empresa {

    private int idEmpresa;
    public static int contador = 0;
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

    public int getIdEmpresa() {
        return contador;
    }

}
