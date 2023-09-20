package org.example.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.example.ContaInexistenteException;
import org.example.dao.ContaBancariaDAO;
import org.example.model.ContaBancaria;

public class ContaBancariaController {
    private ContaBancariaDAO contaBancariaDAO;

    public ContaBancariaController() {
        this.contaBancariaDAO = new ContaBancariaDAO();
    }

    public ContaBancaria criarConta(String numeroConta, BigDecimal saldoInicial, int clienteId) throws SQLException {
        return ContaBancariaDAO.criarConta(numeroConta, saldoInicial, clienteId);
    }
    public List<ContaBancaria> listarContasPorCliente(int clienteId) throws SQLException {
        return contaBancariaDAO.listarContasPorCliente(clienteId);
    }

    public boolean realizarDeposito(int contaId, BigDecimal valor) throws SQLException {
        ContaBancaria conta = contaBancariaDAO.buscarContaPorId(contaId);

        if (conta != null) {
            BigDecimal novoSaldo = conta.getSaldo().add(valor);
            conta.setSaldo(novoSaldo);

            return contaBancariaDAO.atualizarSaldo(conta);
        }

        return false;
    }

    public boolean realizarSaque(int contaId, BigDecimal valor) throws SQLException {
        ContaBancaria conta = contaBancariaDAO.buscarContaPorId(contaId);

        if (conta != null && conta.getSaldo().compareTo(valor) >= 0) {
            BigDecimal novoSaldo = conta.getSaldo().subtract(valor);
            conta.setSaldo(novoSaldo);

            return contaBancariaDAO.atualizarSaldo(conta);
        }

        return false;
    }
    public BigDecimal verificarSaldo(int contaId) throws SQLException {
        return contaBancariaDAO.verificarSaldo(contaId);
    }

    public boolean verificarExistenciaConta(int contaId) throws ContaInexistenteException, SQLException {
        ContaBancaria conta = contaBancariaDAO.buscarContaPorId(contaId);

        if (conta != null) {
            return true;
        } else {
            throw new ContaInexistenteException("A conta com ID " + contaId + " não existe.");
        }
    }

    public BigDecimal verificarSaldoConta(int contaId) throws SQLException {
        ContaBancaria conta = contaBancariaDAO.buscarContaPorId(contaId);

        if (conta != null) {
            return conta.getSaldo();
        } else {
            return null;
        }
    }

}

