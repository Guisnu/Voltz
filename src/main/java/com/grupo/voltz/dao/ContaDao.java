package com.grupo.voltz.dao;

import com.grupo.voltz.services.ConnectionService;

import java.sql.Connection;
import java.sql.SQLException;

public class ContaDao {

    private final Connection conexao;

    public ContaDao() throws SQLException{
        conexao = ConnectionService.getConnection();
    }
}
