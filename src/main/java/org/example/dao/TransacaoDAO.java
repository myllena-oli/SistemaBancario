package org.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.example.model.Transacao;
import org.example.DatabaseConnection;
import java.sql.Timestamp;



public class TransacaoDAO {

    public List<Transacao> listarTransacoesPorConta(int contaId) throws SQLException {
        List<Transacao> transacoes = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Transacao WHERE conta_origem_id = ? OR conta_destino_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, contaId);
            statement.setInt(2, contaId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                LocalDateTime data = resultSet.getTimestamp("data").toLocalDateTime();
                BigDecimal valor = resultSet.getBigDecimal("valor");
                int contaOrigemId = resultSet.getInt("conta_origem_id");
                int contaDestinoId = resultSet.getInt("conta_destino_id");

                Transacao transacao = new Transacao(id, data, valor, contaOrigemId, contaDestinoId);
                transacoes.add(transacao);
            }

        }

        return transacoes;
    }

    public boolean inserirTransacao(Transacao transacao) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Transacao (data, valor, conta_origem_id, conta_destino_id) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1, Timestamp.valueOf(transacao.getData()));
            statement.setBigDecimal(2, transacao.getValor());
            statement.setInt(3, transacao.getContaOrigemId());
            statement.setInt(4, transacao.getContaDestinoId());

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        }
    }

}

