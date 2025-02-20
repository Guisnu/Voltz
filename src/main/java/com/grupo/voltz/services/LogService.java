package com.grupo.voltz.services;

import com.grupo.voltz.model.Carteira;
import com.grupo.voltz.model.Transacao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LogService {

    private static final String LOG_FILE_PATH = "transacoes_log.txt";

    public static void registrarTransacoes(List<Transacao> transacoes, Carteira carteiraEVTransacoes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write("Log de Transações carteira: " + carteiraEVTransacoes.getNome() + "\n");
            writer.write("----------------------------------------------\n");
            for (Transacao transacao : transacoes) {
                String logEntry = String.format(
                        "Tipo: %s | Criptoativo: %s | Quantidade: %.8f | Valor: %.2f | Data: %s\n",
                        transacao.getTipo(),
                        transacao.getCriptoativo().getSimbolo(),
                        transacao.getQuantidade(),
                        transacao.getValor(),
                        transacao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                );
                writer.write(logEntry);
            }
            writer.write("----------------------------------------------\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }
}
