package com.grupo.voltz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private String login;
    private String senha;

    private static final String URL = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

    private static final String USUARIO = "rm554327";

    private static final String SENHA = "020604";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USUARIO,SENHA);
    }
}
