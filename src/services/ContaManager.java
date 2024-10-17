package services;

import model.Conta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContaManager {
    private List<Conta> contas = new ArrayList<>();
    private Random random = new Random();

    public Conta criarConta(String nome, String email, String senha) { //construtor de conta inicializando as strings, o id utilizando RANDOM, e a criação da propria
        Long id = random.nextLong(); // gera um ID aleatório 
        Conta novaConta = new Conta(id, nome, email, senha);
        contas.add(novaConta);
        return novaConta; //método de criação pode ser add na main acc utilizando o scanner
    }

    public List<Conta> getContas() {
        return contas; // teste
    }
}
