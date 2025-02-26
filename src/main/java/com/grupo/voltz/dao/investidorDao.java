package com.grupo.voltz.dao;

import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.SQLException;

public class investidorDao {
    private  Connection conexao;

    public investidorDao() throws SQLException {
        conexao = ConnectionService.getConnection();
    }
}
