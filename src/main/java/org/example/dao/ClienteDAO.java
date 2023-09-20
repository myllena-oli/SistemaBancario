package org.example.dao;

import org.example.DatabaseConnection;
import org.example.model.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteDAO {

    public boolean verificarExistenciaCliente(int clienteId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id FROM Cliente WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clienteId);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        }
    }
    public boolean criarCliente(Cliente cliente) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Cliente (nome, cpf, endereco) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getCpf());
            statement.setString(3, cliente.getEndereco());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }
}

