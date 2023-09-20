package org.example.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.example.DatabaseConnection;
import org.example.model.ContaBancaria;

public class ContaBancariaDAO {



    public static ContaBancaria criarConta(String numeroConta, BigDecimal saldoInicial, int clienteId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO ContaBancaria (numero_conta, saldo, cliente_id) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, numeroConta);
            statement.setBigDecimal(2, saldoInicial);
            statement.setInt(3, clienteId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    return new ContaBancaria(id, numeroConta, saldoInicial, clienteId);
                }
            }
        }
        return null;
    }

    public List<ContaBancaria> listarContasPorCliente(int clienteId) throws SQLException {
        List<ContaBancaria> contas = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM ContaBancaria WHERE cliente_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, clienteId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String numeroConta = resultSet.getString("numero_conta");
                BigDecimal saldo = resultSet.getBigDecimal("saldo");

                ContaBancaria conta = new ContaBancaria(id, numeroConta, saldo, clienteId);
                contas.add(conta);
            }
        }

        return contas;
    }

    public BigDecimal verificarSaldo(int contaId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT saldo FROM ContaBancaria WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, contaId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getBigDecimal("saldo");
            }
        }
        return null;
    }


    public boolean atualizarSaldo(ContaBancaria conta) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE ContaBancaria SET saldo = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, conta.getSaldo());
            statement.setInt(2, conta.getId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        }
    }

    public ContaBancaria buscarContaPorId(int contaId) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM ContaBancaria WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, contaId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String numeroConta = resultSet.getString("numero_conta");
                BigDecimal saldo = resultSet.getBigDecimal("saldo");
                int clienteId = resultSet.getInt("cliente_id");

                return new ContaBancaria(id, numeroConta, saldo, clienteId);
            }
        }

        return null;
    }
}
