package model;

public class Investidor {
    public Conta conta;
    private String nome;
    private String email;
    private String senha;

    public Investidor(Integer id, String nome, String email, String senha) {
        this.conta = new Conta (id, this); // falta atribuir os dados de conta automaticamente
        this.nome = nome;
        this.email = email;
        this.senha = senha;
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

}
