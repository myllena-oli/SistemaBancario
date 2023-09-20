package org.example.controller;

import org.example.dao.ClienteDAO;
import org.example.model.Cliente;
import java.sql.SQLException;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    public boolean verificarExistenciaCliente(int clienteId) throws SQLException {
        return clienteDAO.verificarExistenciaCliente(clienteId);
    }
    public boolean criarCliente(String nome, String cpf, String endereco) throws SQLException {
        Cliente novoCliente = new Cliente(0, nome, cpf, endereco);
        return clienteDAO.criarCliente(novoCliente);
    }
}
