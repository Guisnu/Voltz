package com.grupo.voltz.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {

    private static final String URL = "";

    private static final String USUARIO = ""; //Todo: user sqlserver oracle com "rm ou pf" na frente;

    private static final String SENHA = ""; //Todo: senha sqlserver oracle;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USUARIO,SENHA);
    }

}
