package com.grupo.voltz.view;

import com.grupo.voltz.dao.*;
import com.grupo.voltz.enums.TipoTransacaoEnum;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import com.grupo.voltz.model.*;
import com.grupo.voltz.services.LogService;
import com.grupo.voltz.services.MercadoService;

public class Main {

    public static void main(String[] args) throws SQLException {
        boolean SairMenuDao = false;
        ContaDao contadao = null;
        CarteiraDao carteiraDao = null;
        CriptoativoDao criptoativoDao = null;
        TransacaoDao transacaoDao = null;
        CarteiraCriptoativoDao carteiraCriptoDao = null;
        Scanner sc = new Scanner(System.in);
        Conta conta = null;

        try {
            contadao = new ContaDao();
            carteiraDao = new CarteiraDao();
            criptoativoDao = new CriptoativoDao();
            transacaoDao = new TransacaoDao();
            carteiraCriptoDao = new CarteiraCriptoativoDao();
            System.out.println("--------------------------------------");
            System.out.println("✅ Conexão realizada com sucesso ✅");
            System.out.println("--------------------------------------");
            System.out.println("Pressione ENTER para continuar...");
            sc.nextLine();
        } catch (SQLException e) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Erro ao realizar conexão. Verifique sua ConnectionService!❌");
            System.out.println("-------------------------------------------------------------");
            System.err.println(e);
            System.exit(1);
        }

        MercadoService mercado = new MercadoService();
        List<Criptoativo> criptoativosMercado = mercado.executar();

        while (true) {
            if (conta == null) {
                int opAcesso;
                do {
                    System.out.println("--------------------------------------");
                    System.out.println("|           MENU DE ACESSO           |");
                    System.out.println("--------------------------------------");
                    System.out.println("1 - Login");
                    System.out.println("2 - Cadastro");
                    System.out.println("0 - Sair do Sistema");
                    System.out.print("\nEscolha uma opção: ");
                    opAcesso = sc.nextInt();
                    sc.nextLine(); // Consumir quebra de linha

                    switch (opAcesso) {
                        case 1:
                            // Fluxo de login
                            System.out.print("Digite seu email: ");
                            String email = sc.nextLine();
                            System.out.print("Digite sua senha: ");
                            String senha = sc.nextLine();

                            conta = contadao.login(email, senha);

                            if (conta == null) {
                                System.out.println("\nEmail ou senha inválidos. Tente novamente. 🤦\n");
                            } else {
                                carteiraDao.recuperarCarteiras(conta);

                                for (Carteira carteira : conta.getCarteiras()) {
                                    List<Criptoativo> criptoativos = carteiraCriptoDao.listarCriptoativosPorCarteira(carteira.getIdCarteira());
                                    for (Criptoativo c : criptoativos) {
                                        carteira.adicionarCriptoativo(c);
                                    }
                                }

                                if (conta.getNomeInvestidor() == null || conta.getNomeInvestidor().trim().isEmpty()) {
                                    conta.setNomeInvestidor("Investidor");
                                }
                                System.out.println("\nLogin realizado com sucesso!\n");
                            }

                            break;

                        case 2:
                            // Fluxo de cadastro
                            System.out.print("Digite seu nome: ");
                            String nome = sc.nextLine();
                            System.out.print("Digite seu email: ");
                            String emailCadastro = sc.nextLine();
                            System.out.print("Digite sua senha: ");
                            String senhaCadastro = sc.nextLine();

                            Conta novaConta = new Conta(nome, emailCadastro, senhaCadastro);

                            try {
                                contadao.cadastrar(novaConta);
                                System.out.println("\nCadastro realizado com sucesso!\n");
                            } catch (SQLException e) {
                                System.out.println("Erro no cadastro: " + e.getMessage());
                            }
                            break;

                        case 0:
                            System.out.println("Encerrando o sistema...");
                            sc.close();
                            System.exit(0);
                            break;

                        default:
                            System.out.println("\nOpção inválida.\n");
                    }
                } while (conta == null);
            } else {
                // Menu Principal
                int opPrincipal;
                do {
                    List<Carteira> carteirasInvestidor = conta.getCarteiras();
                    if (!carteirasInvestidor.isEmpty()){
                        System.out.println("\n--- CRIPTOATIVOS NA SUA CONTA ---");

                        for (Carteira carteira : conta.getCarteiras()) {
                            System.out.println("Carteira: " + carteira.getNome());

                            if (carteira.getCriptoativos().isEmpty()) {
                                System.out.println("  Nenhum criptoativo nesta carteira.");
                            } else {
                                for (Criptoativo c : carteira.getCriptoativos()) {
                                    System.out.printf("  - %s (%s): %.6f unidades | Preço atual: R$%.2f%n",
                                            c.getNome(), c.getSimbolo(), c.getQuantidade(), c.getPrecoAtual());
                                }
                            }
                        }
                        System.out.println("----------------------------------");
                    }

                    System.out.println("----------------------------------------------");
                    System.out.println("|                MENU PRINCIPAL               |");
                    System.out.println("----------------------------------------------");
                    System.out.println("|  1  | Depositar                             |");
                    System.out.println("|  2  | Visualizar saldo Conta                |");
                    System.out.println("|  3  | Sacar                                 |");
                    System.out.println("|  4  | Criar carteira de investimento        |");


                    if (!carteirasInvestidor.isEmpty()) {
                        System.out.println("|  5  | Visualizar carteiras de investimento  |");
                        System.out.println("|  6  | Transferir valor para carteira        |");
                        System.out.println("|  7  | Deletar carteira                      |");
                        System.out.println("----------------------------------------------");
                        System.out.println("|                CRIPTOATIVOS                 |");
                        System.out.println("----------------------------------------------");
                        System.out.println("|  8  | Comprar criptoativos                  |");
                        System.out.println("|  9  | Vender criptoativos                   |");
                        System.out.println("| 10  | Visualizar criptoativos nas carteiras |");
                        System.out.println("| 11  | Visualizar transações                 |");
                    }
                    System.out.println("| 100 | Teste do Banco                        |");
                    System.out.println("|  0  | Sair                                  |");

                    System.out.print("Escolha uma opção: ");
                    opPrincipal = sc.nextInt();

                    switch (opPrincipal) {
                        case 1:
                            System.out.print("Informe o valor do depósito: ");
                            double deposito = sc.nextDouble();
                            if (deposito <= 0) {
                                System.out.println("Valor inválido! Deve ser maior que zero.");
                                break;
                            }

                            try {
                                // Atualiza o saldo no objeto
                                conta.depositar(deposito);

                                // Atualiza o saldo no banco
                                contadao.atualizarSaldo(conta.getIdConta(), conta.getSaldo());

                                System.out.printf("Depósito de R$%.2f realizado! Saldo atual: R$%.2f%n",
                                        deposito, conta.getSaldo());
                            } catch (SQLException e) {
                                System.out.println("Erro ao depositar: " + e.getMessage());
                            }
                            break;

                        case 2:
                            System.out.println("Saldo atual: R$" + conta.getSaldo());
                            break;

                        case 3:
                            System.out.print("Informe o valor do saque: ");
                            double saque = sc.nextDouble();
                            if (saque <= 0) {
                                System.out.println("Valor inválido! Deve ser maior que zero.");
                                break;
                            }

                            try {
                                // Tenta sacar (verifica saldo suficiente)
                                if (conta.sacar(saque)) {
                                    // Atualiza o saldo no banco
                                    contadao.atualizarSaldo(conta.getIdConta(), conta.getSaldo());
                                    System.out.printf("Saque de R$%.2f realizado! Saldo atual: R$%.2f%n",
                                            saque, conta.getSaldo());
                                } else {
                                    System.out.println("Saldo insuficiente!");
                                }
                            } catch (SQLException e) {
                                System.out.println("Erro ao sacar: " + e.getMessage());
                            }
                            break;

                        case 4:
                            System.out.print("Informe o nome da carteira: ");
                            String nomeCarteira = sc.next() + sc.nextLine();
                            try {
                                Carteira novaCarteira = new Carteira(nomeCarteira);

                                // Cadastra no banco
                                carteiraDao.cadastrar(conta.getIdConta(), novaCarteira);
                                Integer id = carteiraDao.buscarIdCarteiraPorNome(nomeCarteira);
                                novaCarteira.setIdCarteira(id);

                                // Adiciona à conta (em memória)
                                conta.adicionarCarteira(novaCarteira);

                                // Atualiza a quantidade no banco
                                contadao.atualizarQuantidadeCarteiras(
                                        conta.getIdConta(),
                                        conta.getCarteiras().size()
                                );

                                System.out.println("Carteira criada com sucesso!");
                            } catch (Exception e) {
                                System.out.println("Erro ao criar carteira: " + e.getMessage());
                            }
                            break;

                        case 100:
                            SairMenuDao = false;
                            while(!SairMenuDao){
                                int opDao;
                                System.out.println(conta.getIdConta());
                                System.out.println("---------------------------------------");
                                System.out.println("|           TESTE DO BANCO            |");
                                System.out.println("---------------------------------------");
                                System.out.println("|              CONTADAO                |");
                                System.out.println("|  1  | Mudar nome de usuario          |");
                                System.out.println("|  2  | Lista usuarios                 |");
                                System.out.println("|  3  | Deletar conta                  |");
                                System.out.println("|                                      |");
                                System.out.println("|             CARTEIRADAO              |");
                                System.out.println("|  4  | Mudar nome da carteira         |");
                                System.out.println("|              CRIPTODAO               |");
                                System.out.println("|  5  | Adicionar cripto               |");
                                System.out.println("|  0  | Sair                           |");

                                opDao =  sc.nextInt();

                                switch (opDao) {
                                    case 1:
                                        System.out.println("Nome atual: " + conta.getNomeInvestidor());
                                        System.out.print("Digite o novo nome de investidor: ");
                                        sc.nextLine();
                                        String novoNome = sc.nextLine();

                                        if (novoNome.isEmpty()) {
                                            System.out.println("Nome inválido! O nome não pode ser vazio.");
                                            break;
                                        }

                                        try {
                                            contadao.atualizarNomeInvestidor(conta.getIdConta(), novoNome);
                                            conta.setNomeInvestidor(novoNome);
                                            System.out.println("Nome atualizado com sucesso!");
                                        } catch (SQLException e) {
                                            System.out.println("Erro ao atualizar nome: " + e.getMessage());
                                        }
                                        break;

                                    case 2:
                                        List<Conta> contas = contadao.listar();
                                        for (Conta user : contas) {
                                            System.out.println("nome: " + user.getNomeInvestidor() + ", " + "Email: " + user.getEmailInvestidor() + ", " + "senha: " + user.getSenhaInvestidor() + ", " + "saldo: " + "R$" + user.getSaldo() + ", " + "carteiras: " + user.getCarteiras().size());
                                        }
                                        break;

                                    case 3:
                                        System.out.print("Tem certeza que deseja excluir sua conta? (S/N): ");
                                        sc.nextLine();
                                        String confirmacao = sc.nextLine().trim().toUpperCase();

                                        if (!confirmacao.equals("S")) {
                                            System.out.println("Operação cancelada.");
                                            break;
                                        }

                                        try {
                                            contadao.deletarConta(conta.getIdConta());
                                            System.out.println("Conta deletada com sucesso! Retornando ao menu de login...");
                                            SairMenuDao = true;
                                            conta = null;
                                            opPrincipal = 0;
                                            continue;
                                        } catch (SQLException e) {
                                            System.out.println("Erro ao excluir conta: " + e.getMessage());
                                        }
                                        break;

                                    case 4:

                                        if (carteirasInvestidor.isEmpty()) {
                                            System.out.println("Opção inválida! Cadastre uma carteira primeiro.");
                                            break;
                                        }else{
                                            System.out.println("Carteiras atuais");
                                            for (Carteira carteira : carteirasInvestidor) {
                                                System.out.println("Nome: " + carteira.getNome() + ", Saldo: R$" + carteira.getSaldo());
                                            }
                                            System.out.println("Digite o nome da carteira que deseja alterar: ");
                                            sc.nextLine();
                                            String nomeAtualCarteira = sc.nextLine();
                                            System.out.println("Digite o novo nome da carteira: ");
                                            String novoNomeCarteira = sc.nextLine();
                                            carteiraDao.atualizarNome(nomeAtualCarteira, novoNomeCarteira);

                                        }

                                        break;

                                    case 5:
                                        sc.nextLine();
                                        System.out.println("Digite o nome do criptoativo: ");
                                        String nomecripto =  sc.nextLine();
                                        System.out.println("Digite o simbolo do criptoativo: ");
                                        String simbolocripto =  sc.nextLine();
                                        System.out.println("Digite o valor do criptoativo: ");
                                        Double valorcripto =  sc.nextDouble();
                                        criptoativoDao.cadastrar(nomecripto, simbolocripto, valorcripto);
                                        break;

                                    case 0:
                                        System.out.println("Voltando para o menu...");
                                        SairMenuDao = true;
                                }
                            }
                            break;

                        case 0:
                            System.out.println("Saindo da conta...");
                            conta = null; // Desloga o usuário
                            break;

                        default:
                            if (carteirasInvestidor.isEmpty()) {
                                System.out.println("Opção inválida! Cadastre uma carteira primeiro.");
                                break;
                            }

                            switch (opPrincipal){
                                case 5:
                                    System.out.println("Carteiras disponíveis:");
                                    for (Carteira carteira : carteirasInvestidor) {
                                        System.out.println("Id banco: " + carteira.getIdCarteira() + " Nome: " + carteira.getNome() + ", Saldo: R$" + carteira.getSaldo());
                                    }
                                    break;

                                case 6:
                                    System.out.println("----------------------------------------------");
                                    System.out.println("|         TRANSFERIR VALOR NA CARTEIRA       |");
                                    System.out.println("----------------------------------------------");

                                    Carteira carteiraEscolhida = escolherCarteira(carteirasInvestidor, sc);

                                    if (carteiraEscolhida == null) {
                                        break;
                                    }

                                    System.out.print("Informe a quantidade do depósito: ");
                                    double valorDeposito = sc.nextDouble();

                                    try {
                                        carteiraEscolhida.setSaldo(carteiraEscolhida.getSaldo() + valorDeposito);

                                        int idCarteira = carteiraEscolhida.getIdCarteira();
                                        carteiraDao.atualizarSaldo(idCarteira, valorDeposito);

                                        System.out.println("Valor depositado com sucesso na carteira " + carteiraEscolhida.getNome() + "!");

                                    } catch (Exception e) {
                                        System.out.println("Erro ao depositar: " + e.getMessage());
                                    }
                                    break;

                                case 7:
                                    Carteira carteiraDel = escolherCarteira(carteirasInvestidor, sc);
                                    if (carteiraDel == null) break;

                                    System.out.print("Tem certeza que deseja excluir sua carteira? (S/N): ");
                                    sc.nextLine();
                                    String confirmacao = sc.nextLine().trim().toUpperCase();

                                    if (!confirmacao.equals("S")) {
                                        System.out.println("Operação cancelada.");
                                        break;
                                    }

                                    try {
                                        // Remove do banco
                                        carteiraDao.deletar(carteiraDel.getIdCarteira());

                                        // Remove da conta (em memória)
                                        conta.removerCarteira(carteiraDel);

                                        // Atualiza a quantidade no banco
                                        contadao.atualizarQuantidadeCarteiras(
                                                conta.getIdConta(),
                                                conta.getCarteiras().size()
                                        );

                                        System.out.println("Carteira deletada com sucesso!");
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar carteira: " + e.getMessage());
                                    }
                                    break;

                                case 8:
                                    System.out.println("----------------------------------------------");
                                    System.out.println("|             COMPRAR CRIPTOATIVO            |");
                                    System.out.println("----------------------------------------------");

                                    List<Criptoativo> criptoativosBanco = criptoativoDao.listarTodos();
                                    for (int i = 0; i < criptoativosBanco.size(); i++) {
                                        Criptoativo c = criptoativosBanco.get(i);
                                        System.out.printf("%d- Símbolo: %s | Nome: %s | Preço: R$%.2f%n",
                                                i + 1, c.getSimbolo(), c.getNome(), c.getPrecoAtual());
                                    }

                                    System.out.print("Escolha um criptoativo para comprar (número): ");
                                    int escolhaCripto = sc.nextInt();
                                    if (escolhaCripto < 1 || escolhaCripto > criptoativosBanco.size()) {
                                        System.out.println("Opção inválida!");
                                        break;
                                    }

                                    Criptoativo criptoativoEscolhido = criptoativosBanco.get(escolhaCripto - 1);

                                    Carteira carteiraECompra = escolherCarteira(carteirasInvestidor, sc);
                                    if (carteiraECompra == null) break;

                                    System.out.print("Informe o valor a ser comprado (R$): ");
                                    double valorCompra = sc.nextDouble();
                                    if (valorCompra <= 0) {
                                        System.out.println("Valor inválido! Deve ser maior que zero.");
                                        break;
                                    }

                                    if (valorCompra > carteiraECompra.getSaldo()) {
                                        System.out.println("Saldo insuficiente na carteira!");
                                        break;
                                    }

                                    double quantidadeComprada = valorCompra / criptoativoEscolhido.getPrecoAtual();
                                    System.out.printf("[DEBUG] Quantidade calculada: %.8f %s%n",
                                            quantidadeComprada, criptoativoEscolhido.getSimbolo());

                                    try {
                                        carteiraECompra.setSaldo(carteiraECompra.getSaldo() - valorCompra);
                                        carteiraDao.atualizarSaldo(carteiraECompra.getIdCarteira(), -valorCompra);

                                        carteiraCriptoDao.registrarCompra(
                                                carteiraECompra.getIdCarteira(),
                                                criptoativoEscolhido.getIdCriptoativo(),
                                                quantidadeComprada
                                        );

                                        boolean criptoJaExiste = false;
                                        for (Criptoativo c : carteiraECompra.getCriptoativos()) {
                                            if (c.getIdCriptoativo() == criptoativoEscolhido.getIdCriptoativo()) {
                                                c.setQuantidade(c.getQuantidade() + quantidadeComprada);
                                                criptoJaExiste = true;
                                                break;
                                            }
                                        }
                                        if (!criptoJaExiste) {
                                            Criptoativo novoCripto = new Criptoativo(
                                                    criptoativoEscolhido.getIdCriptoativo(),
                                                    criptoativoEscolhido.getNome(),
                                                    criptoativoEscolhido.getSimbolo(),
                                                    criptoativoEscolhido.getPrecoAtual()
                                            );
                                            novoCripto.setQuantidade(quantidadeComprada);
                                            carteiraECompra.adicionarCriptoativo(novoCripto);
                                        }

                                        Transacao compra = new Transacao(TipoTransacaoEnum.COMPRA, criptoativoEscolhido, quantidadeComprada, valorCompra);
                                        transacaoDao.cadastrar(compra, conta.getIdConta());

                                        System.out.println("Compra realizada com sucesso!");

                                    } catch (SQLException e) {
                                        System.out.println("Erro ao comprar: " + e.getMessage());
                                    }
                                    break;

                                case 9:
                                    System.out.println("----------------------------------------------");
                                    System.out.println("|              VENDER CRIPTOATIVO            |");
                                    System.out.println("----------------------------------------------");

                                    Carteira carteiraEVenda = escolherCarteira(carteirasInvestidor, sc);
                                    if (carteiraEVenda == null) break;

                                    Criptoativo criptoVenda = escolherCriptoativoCarteira(carteiraEVenda, sc);
                                    if (criptoVenda == null) break;

                                    System.out.printf("Informe a quantidade de %s a vender (Disponível: %.8f): ",
                                            criptoVenda.getSimbolo(), criptoVenda.getQuantidade());
                                    double quantidadeVenda = sc.nextDouble();


                                    if (quantidadeVenda <= 0 || quantidadeVenda > criptoVenda.getQuantidade()) {
                                        System.out.println("Quantidade inválida ou saldo insuficiente!");
                                        break;
                                    }

                                    double valorVenda = quantidadeVenda * criptoVenda.getPrecoAtual();
                                    System.out.printf("Valor da venda: R$%.2f%n", valorVenda);

                                    try {
                                        carteiraCriptoDao.atualizarQuantidade(
                                                carteiraEVenda.getIdCarteira(),
                                                criptoVenda.getIdCriptoativo(),
                                                criptoVenda.getQuantidade() - quantidadeVenda
                                        );

                                        carteiraEVenda.setSaldo(carteiraEVenda.getSaldo() + valorVenda);
                                        carteiraDao.atualizarSaldo(carteiraEVenda.getIdCarteira(), valorVenda);

                                        Transacao venda = new Transacao(TipoTransacaoEnum.VENDA, criptoVenda, quantidadeVenda, valorVenda);
                                        transacaoDao.cadastrar(venda, conta.getIdConta());

                                        criptoVenda.setQuantidade(criptoVenda.getQuantidade() - quantidadeVenda);

                                        System.out.println("Venda realizada com sucesso!");

                                    } catch (SQLException e) {
                                        System.out.println("Erro ao vender: " + e.getMessage());
                                    }
                                    break;
                                case 10:
                                    System.out.println("----------------------------------------------");
                                    System.out.println("|    VISUALIZAR CRIPTOATIVOS DA CARTEIRA     |");
                                    System.out.println("----------------------------------------------");

                                    Carteira carteiraEVCriptoativo = escolherCarteira(carteirasInvestidor, sc);

                                    if (carteiraEVCriptoativo == null) {
                                        break;
                                    }

                                    System.out.println("=== CRIPTOATIVOS NA CARTEIRA ===");
                                    List<Criptoativo> criptoativosCarteiraE = carteiraEVCriptoativo.getCriptoativos();

                                    if (criptoativosCarteiraE.isEmpty()) {
                                        System.out.println("Nenhum criptoativo na carteira.");
                                        break;
                                    }

                                    for (Criptoativo criptoativo : criptoativosCarteiraE) {
                                        System.out.printf(
                                                "Símbolo: %s | Nome: %s | Quantidade: %.8f | Preço atual: R$%.2f%n",
                                                criptoativo.getSimbolo(),
                                                criptoativo.getNome(),
                                                criptoativo.getQuantidade(),
                                                criptoativo.getPrecoAtual()
                                        );
                                    }
                                    break;
                                case 11:
                                    System.out.println("----------------------------------------------");
                                    System.out.println("|            VISUALIZAR TRANSAÇÕES           |");
                                    System.out.println("----------------------------------------------");

                                    Carteira carteiraEVTransacoes = escolherCarteira(carteirasInvestidor, sc);

                                    if (carteiraEVTransacoes == null) {
                                        break;
                                    }

                                    List<Transacao> transacoesCarteira = carteiraEVTransacoes.getTransacoes();

                                    for (Transacao transacao : transacoesCarteira) {
                                        System.out.printf(
                                                "Tipo: %s | Criptoativo: %s | Quantidade: %.8f | Valor: %.2f | Data: %s%n",
                                                transacao.getTipo(),
                                                transacao.getCriptoativo().getSimbolo(),
                                                transacao.getQuantidade(),
                                                transacao.getValor(),
                                                transacao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                                        );
                                    }

                                    LogService.registrarTransacoes(transacoesCarteira, carteiraEVTransacoes);
                                    break;

                                default:
                                    System.out.println("Opção inválida!");
                            }
                    }

                    if (opPrincipal != 0) {
                        voltarMenuPrincipal(sc);
                    }

                } while (opPrincipal != 0 && conta != null);
            }
        }
    }



    private static Carteira escolherCarteira(List<Carteira> carteiras, Scanner sc) {

        if (carteiras.isEmpty()) {
            System.out.println("Nenhuma carteira foi encontrada!");
            return null;
        }

        for (int i = 0; i < carteiras.size(); i++) {
            Carteira c = carteiras.get(i);
            System.out.printf("%d- Nome: %s | Saldo: R$%.2f%n", i + 1, c.getNome(), c.getSaldo());
        }

        System.out.print("Escolha uma carteira de investimento (número): ");
        int carteiraEVCriptoativoSc = sc.nextInt();

        if (carteiraEVCriptoativoSc < 1 || carteiraEVCriptoativoSc > carteiras.size()) {
            System.out.println("Opção inválida!");
            return null;
        }

        return carteiras.get(carteiraEVCriptoativoSc - 1);
    }

    private static Criptoativo escolherCriptoativoCarteira(Carteira carteira, Scanner sc) {

        System.out.println("Criptoativos na carteira:");
        List<Criptoativo> criptoativosCarteira = carteira.getCriptoativos();

        if (criptoativosCarteira.isEmpty()) {
            System.out.println("Nenhum criptoativo encontrado!");
            return null;
        }

        for (int j = 0; j < criptoativosCarteira.size(); j++) {
            Criptoativo ca = criptoativosCarteira.get(j);
            System.out.printf("%d- Símbolo: %s | Nome: %s | Quantidade: %.8f%n",
                    j + 1, ca.getSimbolo(), ca.getNome(), ca.getQuantidade());
        }

        System.out.print("Escolha um criptoativo (número): ");
        int escolhaCriptoativo = sc.nextInt();
        if (escolhaCriptoativo < 1 || escolhaCriptoativo > criptoativosCarteira.size()) {
            System.out.println("Opção inválida!");
            return null;
        }

        return criptoativosCarteira.get(escolhaCriptoativo - 1);
    }

    private static void voltarMenuPrincipal(Scanner sc) {
        System.out.println("Pressione ENTER para voltar ao menu principal...");
        sc.nextLine();
        sc.nextLine();
    }
}
