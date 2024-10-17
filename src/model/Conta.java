package model;

import enums.TipoMovimentacaoEnum;

import java.util.ArrayList;
import java.util.List;

public class Conta extends Investidor {
    private Loing id; //uso do Long é para poder representar a ausência de um id caso necessário, assim, retornando NULL. ID = CHAVE PRIMÁRIA, agora vou dar um get nele e usar o construtor ED
    private double saldo = 0.0;
    private List<Empresa> empresas = new ArrayList<Empresa>();
    private List<Carteira> carteiras = new ArrayList<Carteira>();
    private List<MovimentacaoConta> movimentacoes = new ArrayList<MovimentacaoConta>();
    private List<Transacao> transacoes = new ArrayList<Transacao>();

    public Conta(String nome, String email, String senha) {
        super(nome, email, senha);
        this.id = id; //add o id no construtor, para ser inicializado junto ED
    } 

    public Long getId() {  //get id básico ED
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public void adicionarCarteira(Carteira carteira) {
        carteiras.add(carteira);
    }

    public void removerCarteira(Carteira carteira) {
        carteiras.remove(carteira);
    }

    public void adicionarEmpresa(Empresa empresa) {
        empresas.add(empresa);
    }

    public void removerEmpresa(Empresa empresa) {
        empresas.remove(empresa);
    }

    public List<Empresa> getEmpresas() {
        System.out.println("===EMPRESAS===");

        for (Empresa empresa : empresas) {
            System.out.println("Razão Social: " + empresa.getRazaoSocial() + ", CNPJ: " + empresa.getCnpj());
        }

        return empresas;
    }

    public void adicionarMovimentacao(MovimentacaoConta movimentacaoConta) {
        movimentacoes.add(movimentacaoConta);
    }

    public List<MovimentacaoConta> getMovimentacoes() {
        System.out.println("===MOVIMENTAÇÕES NA CONTA===");

        for (MovimentacaoConta movimentacao : movimentacoes) {
            System.out.println("Tipo: " + movimentacao.getTipo() + ", Valor: " + movimentacao.getValor());
        }

        return movimentacoes;
    }

    public boolean depositar(double valor) {
        if (valor < 0){
            System.out.println("O valor deve ser maior que Zero!");
            return false;
        }
        adicionarMovimentacao(new MovimentacaoConta(this, TipoMovimentacaoEnum.DEPOSITO, valor));

        this.saldo += valor;
        return true;

    }

    public boolean sacar(double valor) {
        if (this.saldo >= valor && this.saldo > 0) {
            this.saldo -= valor;
            return true;
        }
        return false;
    }

    public List<Transacao> visualizarHistorico() {
        List<Transacao> historico = new ArrayList<>();
        for (Transacao transacao : transacoes) {
            historico.add(transacao);
        }
        return historico;
    }

    @Override
    public String toString() {
        return "Conta={" +
                "id=" + id + //nao precisa do get pois ja fiz la em cima ED
                "nome=" + getNome() +
                ", email=" + getEmail() +
                ", senha='" + getSenha() + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}


