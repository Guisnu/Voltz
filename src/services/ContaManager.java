// package services;
// import model.Conta;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// public class ContaManager {
//     private List<Conta> contas = new ArrayList<>(); //inicializou uma lista vazia com a variável conta,
//     private Random random = new Random(); //Inicializou a classe RANDOM que é usada para gerar numeros aleatórios em java

//     public Conta criarConta(String nome, String email, String senha) { //construtor de conta inicializando as strings, o id utilizando RANDOM, e a criação da propria
//         Integer id = random.nextLong(); // gera um ID aleatório 
//         Conta novaConta = new Conta(id, nome, email, senha);  //INICIALIZADOR DO ID, NOME E ETC
//         contas.add(novaConta);
//         return novaConta; //método de criação pode ser add na main acc utilizando o scanner
//     }

//     public List<Conta> getContas() {     //TODO ESSE CÓDIGO (ContaManager) VAI SER INICIALIZADO NA MAIN PARA PROCESSO DE CRIAÇÃO DA CONTA!
//         return contas; // teste
//     }
// }
