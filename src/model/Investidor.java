package model;

public class Investidor {

    private static int idInvestidor = 0;
    public Conta conta;
    private String nome;
    private String email;
    private String senha;

    public Investidor(String nome, String email, String senha) {
        this.conta = new Conta(this.idInvestidor, this);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public static int getId() {
        return ++idInvestidor;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean verificarIdConta() {
        return conta.getId().equals(this.idInvestidor);
    }
}
