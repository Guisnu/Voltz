package com.grupo.voltz.view;

import com.grupo.voltz.dao.ContaDao;
import com.grupo.voltz.enums.TipoOrdemEnum;
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

        int op;
        ContaDao dao = null;
        Scanner sc = new Scanner(System.in);

        try {
            dao = new ContaDao();
            System.out.println("--------------------------------------");
            System.out.println("✅ Conexão realizada com sucesso ✅");
            System.out.println("--------------------------------------");
            System.out.println("Pressione ENTER para continuar...");
            sc.nextLine();
        } catch (SQLException e) {
            System.out.println("--------------------------------------------------------------------------------------------");
            System.out.println("❌ Erro ao realizar conexão Verifique o arquivo " + "ConnectionService" + " para realizar a conexão ❌");
            System.out.println("--------------------------------------------------------------------------------------------");
            System.err.println(e.getMessage());
            System.out.println("Pressione ENTER para continuar...");
            sc.nextLine();
            System.exit(1);
        }

        MercadoService mercado = new MercadoService();
        List<Criptoativo> criptoativosMercado = mercado.executar();

//        boolean emailValido = false;
//        String email = "";
//        String nome = "";
//        String senha = "";
//
//        while (!emailValido) {
//            System.out.println("Digite seu email:");
//            email = sc.nextLine();
//
//            try {
//                if (dao.pesquisar_email(email)) {
//                    System.out.println("Email já cadastrado. Tente novamente.");
//                } else {
//                    emailValido = true;
//                }
//            } catch (SQLException e) {
//                System.out.println("Erro ao verificar email: " + e.getMessage());
//            }
//        }
//
//        System.out.println("Digite seu nome:");
//        nome = sc.nextLine();
//
//        System.out.println("Digite sua senha:");
//        senha = sc.nextLine();
//
//        Conta conta = new Conta(nome, email, senha);

        Conta conta = new Conta(
                "teste@",
                "teste"
        );
        Carteira c = new Carteira("gui", conta);
        conta.adicionarCarteira(c);

        dao.cadastrar(conta);


        String nomeInvestidor;

        if (conta.getNomeInvestidor() == null) {
            nomeInvestidor = "Investidor";
        } else {
            nomeInvestidor = conta.getNomeInvestidor();
        }

        System.out.println("            SISTEMA DE INVESTIMENTO         ");
        System.out.println("                  BEM VINDO                 ");

        System.out.println("\nOlá, " + nomeInvestidor + "!\n");

        do {
            List<Carteira> carteirasInvestidor = conta.getCarteiras();

            System.out.println("----------------------------------------------");
            System.out.println("|                MENU PRINCIPAL               |");
            System.out.println("----------------------------------------------");
            System.out.println("|  1  | Depositar                             |");
            System.out.println("|  2  | Visualizar saldo Conta                |");
            System.out.println("|  3  | Sacar                                 |");
            System.out.println("|  4  | Criar carteira de investimento        |");
            System.out.println("| 100 | lista usuarios(administrativo)        |");
            System.out.println("|  0  | Sair                                  |");

            if (!carteirasInvestidor.isEmpty()) {
                System.out.println("|  5  | Visualizar carteiras de investimento  |");
                System.out.println("|  6  | Transferir valor na carteira          |");
                System.out.println("----------------------------------------------");
                System.out.println("|                CRIPTOATIVOS                 |");
                System.out.println("----------------------------------------------");
                System.out.println("|  7  | Comprar criptoativos                  |");
                System.out.println("|  8  | Vender criptoativos                   |");
                System.out.println("|  9  | Visualizar criptoativos nas carteiras |");
                System.out.println("| 10  | Visualizar transações                 |");
                System.out.println("----------------------------------------------");
                System.out.println("|                   ORDENS                    |");
                System.out.println("----------------------------------------------");
                System.out.println("| 11  | Cadastrar ordem de compra             |");
                System.out.println("| 12  | Cadastrar ordem de venda              |");
                System.out.println("| 13  | Cancelar ordem                        |");
                System.out.println("| 14  | Enviar ordem                          |");
                System.out.println("| 15  | Executar ordem                        |");
                System.out.println("| 16  | Visualizar ordens                     |");
            }

            System.out.print("Escolha uma opção: ");
            op = sc.nextInt();

            switch (op) {
                case 1:
                    System.out.println("Informe a quantidade do depósito: ");
                    double deposito = sc.nextDouble();
                    conta.depositar(deposito);
                    System.out.println("Valor depositado com sucesso!");
                    break;

                case 2:
                    System.out.println("Saldo atual: R$" + conta.getSaldo());
                    break;

                case 3:
                    System.out.println("Informe a quantidade do saque: ");
                    double saque = sc.nextDouble();
                    if (conta.sacar(saque)) {
                        System.out.println("Valor sacado com sucesso!");
                    } else {
                        System.out.println("Saldo insuficiente!");
                    }
                    break;

                case 4:
                    System.out.print("Informe o nome da carteira: ");
                    String nomeCarteira = sc.next() + sc.nextLine();
                    try {
                        Carteira novaCarteira = new Carteira(nomeCarteira, conta);
                        conta.adicionarCarteira(novaCarteira);
                        System.out.println("Carteira criada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao criar carteira: " + e.getMessage());
                    }
                    break;

                case 100:
                    List<Conta> contas = dao.listar();
                    for(Conta user : contas){
                        System.out.println("nome: " + user.getNomeInvestidor() + ", " + "Email: " + user.getEmailInvestidor() + ", " + "senha: " + user.getSenhaInvestidor() + ", " + "saldo: " + "R$" + user.getSaldo() + ", " + "carteiras: " + user.getCarteiras().size() + ", " + "movimentacoes: " + user.getMovimentacoes().size());
                    }
                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    // Bloquear opções avançadas se não houver carteiras
                    if (carteirasInvestidor.isEmpty()) {
                        System.out.println("Opção inválida! Cadastre uma carteira primeiro.");
                        break;
                    }

                    switch (op) {
                        case 5:
                            System.out.println("Carteiras disponíveis:");
                            for (Carteira carteira : carteirasInvestidor) {
                                System.out.println("Nome: " + carteira.getNome() + ", Saldo: R$" + carteira.getSaldo());
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
                                carteiraEscolhida.depositar(valorDeposito);
                                System.out.println("Valor depositado com sucesso na carteira " + carteiraEscolhida.getNome() + "!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 7:
                            System.out.println("----------------------------------------------");
                            System.out.println("|             COMPRAR CRIPTOATIVO            |");
                            System.out.println("----------------------------------------------");

                            for (int i = 0; i < criptoativosMercado.size(); i++) {
                                Criptoativo criptoativo = criptoativosMercado.get(i);
                                System.out.printf(
                                        "%d- Símbolo: %s | Nome: %s | Preço: R$%.2f%n",
                                        i + 1,
                                        criptoativo.getSimbolo(),
                                        criptoativo.getNome(),
                                        criptoativo.getPrecoAtual()
                                );
                            }

                            System.out.print("Escolha um criptoativo para comprar (número): ");
                            int escolhaCripto = sc.nextInt();
                            if (escolhaCripto < 1 || escolhaCripto > criptoativosMercado.size()) {
                                System.out.println("Opção inválida!");
                                break;
                            }

                            Criptoativo criptoativoEscolhido = criptoativosMercado.get(escolhaCripto - 1);

                            System.out.println("=== ESCOLHER CARTEIRA ===");
                            Carteira carteiraECompra = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraECompra == null) {
                                break;
                            }

                            System.out.print("Informe o valor a ser comprado: ");
                            double quantidade = sc.nextDouble();

                            if (quantidade < 0) {
                                System.out.println("O valor de compra deve ser maior que Zero!");
                                break;
                            }

                            try {
                                Transacao compraBTC = new Transacao(TipoTransacaoEnum.COMPRA, criptoativoEscolhido, quantidade);
                                compraBTC.executarTransacao(carteiraECompra);
                            } catch (Exception e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 8:
                            System.out.println("----------------------------------------------");
                            System.out.println("|              VENDER CRIPTOATIVO            |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraEVendaCriptoativo = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraEVendaCriptoativo == null) {
                                break;
                            }

                            Criptoativo criptoativoEVenda = escolherCriptoativoCarteira(carteiraEVendaCriptoativo, sc);

                            if (criptoativoEVenda == null) {
                                break;
                            }

                            System.out.print("Informe o valor a ser vendido: ");
                            double valorVenda = sc.nextDouble();

                            try {
                                Transacao compraBTC = new Transacao(TipoTransacaoEnum.VENDA, criptoativoEVenda, valorVenda);
                                compraBTC.executarTransacao(carteiraEVendaCriptoativo);
                            } catch (Exception e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 9:
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
                        case 10:
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
                        case 11:
                            System.out.println("----------------------------------------------");
                            System.out.println("|          CADASTRAR ORDEM DE COMPRA         |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraOCompra = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraOCompra == null) {
                                break;
                            }

                            Criptoativo criptoativoOCompra = escolherCriptoativoCarteira(carteiraOCompra, sc);

                            if (criptoativoOCompra == null) {
                                break;
                            }

                            System.out.print("Informe o preço limite: ");
                            double precoLimite = sc.nextDouble();

                            try {
                                Ordem ordemComprar = new Ordem(TipoOrdemEnum.COMPRA, carteiraOCompra, criptoativoOCompra, precoLimite);
                                ordemComprar.adicionarOrdem();
                                System.out.println("Ordem de compra cadastrada com sucesso!");
                            } catch (Exception e) {
                                System.out.print(e.getMessage());
                            }
                            break;
                        case 12:
                            System.out.println("----------------------------------------------");
                            System.out.println("|           CADASTRAR ORDEM DE VENDA         |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraOVenda = escolherCarteira(carteirasInvestidor, sc);
                            if (carteiraOVenda == null) {
                                break;
                            }

                            Criptoativo criptoativoOVenda = escolherCriptoativoCarteira(carteiraOVenda, sc);
                            if (criptoativoOVenda == null) {
                                break;
                            }

                            System.out.print("Informe o preço limite: ");
                            double precoLimiteVenda = sc.nextDouble();

                            try {
                                Ordem ordemVender = new Ordem(TipoOrdemEnum.VENDA, carteiraOVenda, criptoativoOVenda, precoLimiteVenda);
                                ordemVender.adicionarOrdem();
                                System.out.println("Ordem de venda cadastrada com sucesso!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 13:
                            System.out.println("----------------------------------------------");
                            System.out.println("|                CANCELAR ORDEM              |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraOCancelar = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraOCancelar == null) {
                                break;
                            }

                            Ordem ordemECancelar = escolherOrdensCarteira(carteiraOCancelar, sc);

                            if (ordemECancelar == null) {
                                break;
                            }

                            try {
                                ordemECancelar.cancelarOrdem();
                                System.out.println("Ordem cancelada com sucesso!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 14:
                            System.out.println("----------------------------------------------");
                            System.out.println("|                 ENVIAR ORDEM               |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraOEnviar = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraOEnviar == null) {
                                break;
                            }

                            Ordem ordemEEnviar = escolherOrdensCarteira(carteiraOEnviar, sc);

                            if (ordemEEnviar == null) {
                                break;
                            }

                            try {
                                ordemEEnviar.enviarOrdem();
                                System.out.println("Ordem enviada com sucesso!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 15:
                            System.out.println("----------------------------------------------");
                            System.out.println("|               EXECUTAR ORDEM               |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraOExecutar = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraOExecutar == null) {
                                break;
                            }

                            Ordem ordemEExecutar = escolherOrdensCarteira(carteiraOExecutar, sc);

                            if (ordemEExecutar == null) {
                                break;
                            }

                            try {
                                ordemEExecutar.executarOrdem();
                                System.out.println("Ordem processada com sucesso!");
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                            break;
                        case 16:
                            System.out.println("----------------------------------------------");
                            System.out.println("|             VISUALIZAR ORDEM               |");
                            System.out.println("----------------------------------------------");

                            Carteira carteiraEVOrdens = escolherCarteira(carteirasInvestidor, sc);

                            if (carteiraEVOrdens == null) {
                                break;
                            }

                            List<Ordem> ordensCarteira = carteiraEVOrdens.getOrdens();

                            if (ordensCarteira.isEmpty()) {
                                System.out.println("Nenhuma ordem encontrada!");
                                break;
                            }

                            for (Ordem ordem : ordensCarteira) {
                                System.out.printf(
                                        "Código: %s | Status: %s | Tipo: %s | Criptoativo: %s | Carteira: %s | Preço limite: %.2f | Data: %s%n",
                                        ordem.getCodigo(),
                                        ordem.getStatus(),
                                        ordem.getTipo(),
                                        ordem.getCriptoativo().getSimbolo(),
                                        ordem.getCarteira().getNome(),
                                        ordem.getPrecoLimite(),
                                        ordem.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                                );
                            }
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
            }

            if (op != 0) {
                voltarMenuPrincipal(sc);
            }

        } while (op != 0);

        sc.close();
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

    private static Ordem escolherOrdensCarteira(Carteira carteira, Scanner sc) {

        System.out.println("Ordens na carteira:");
        List<Ordem> ordensCarteira = carteira.getOrdens();

        if (ordensCarteira.isEmpty()) {
            System.out.println("Nenhuma ordem encontrada!");
            return null;
        }

        for (int j = 0; j < ordensCarteira.size(); j++) {
            Ordem ordem = ordensCarteira.get(j);
            System.out.printf("%d- Código: %s | Status: %s | Preço limite: R$%.2f%n",
                    j + 1, ordem.getCodigo(), ordem.getStatus(), ordem.getPrecoLimite());
        }

        System.out.print("Escolha uma ordem (número): ");
        int ordemE = sc.nextInt();
        if (ordemE < 1 || ordemE > ordensCarteira.size()) {
            System.out.println("Opção inválida!");
            return null;
        }

        return ordensCarteira.get(ordemE - 1);
    }

    private static void voltarMenuPrincipal(Scanner sc) {
        System.out.println("Pressione ENTER para voltar ao menu principal...");
        sc.nextLine();
        sc.nextLine();
    }
}
