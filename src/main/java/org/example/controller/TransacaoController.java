package org.example.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.example.ContaInexistenteException;
import org.example.dao.ContaBancariaDAO;
import org.example.dao.TransacaoDAO;
import org.example.model.ContaBancaria;
import org.example.model.Transacao;

public class TransacaoController {
    private TransacaoDAO transacaoDAO;
    private ContaBancariaDAO contaBancariaDAO;
    private ContaBancariaController contaBancariaController;

    public TransacaoController() {
        this.transacaoDAO = new TransacaoDAO();
        this.contaBancariaController = new ContaBancariaController();
        this.contaBancariaDAO = new ContaBancariaDAO();
    }

    public List<Transacao> listarTransacoesPorConta(int contaId) throws SQLException {
        return transacaoDAO.listarTransacoesPorConta(contaId);
    }
    public boolean transferirDinheiro(int contaOrigemId, int contaDestinoId, BigDecimal valor) throws SQLException, ContaInexistenteException {
        boolean contaOrigemExiste = contaBancariaController.verificarExistenciaConta(contaOrigemId);
        boolean contaDestinoExiste = contaBancariaController.verificarExistenciaConta(contaDestinoId);

        if (contaOrigemExiste && contaDestinoExiste) {
            BigDecimal saldoContaOrigem = contaBancariaController.verificarSaldoConta(contaOrigemId);

            if (saldoContaOrigem != null && saldoContaOrigem.compareTo(valor) >= 0) {
                Transacao transacao = new Transacao(valor, contaOrigemId, contaDestinoId);

                System.out.println("Transferência da conta " + contaOrigemId + " para a conta " + contaDestinoId);

                boolean sucessoTransferencia = transacaoDAO.inserirTransacao(transacao);

                ContaBancaria contaOrigem = contaBancariaDAO.buscarContaPorId(contaOrigemId);
                ContaBancaria contaDestino = contaBancariaDAO.buscarContaPorId(contaDestinoId);

                if (contaOrigem != null && contaDestino != null) {
                    BigDecimal novoSaldoContaOrigem = saldoContaOrigem.subtract(valor);
                    BigDecimal novoSaldoContaDestino = contaDestino.getSaldo().add(valor);

                    contaOrigem.setSaldo(novoSaldoContaOrigem);
                    contaDestino.setSaldo(novoSaldoContaDestino);

                    boolean sucessoAtualizacaoContaOrigem = contaBancariaDAO.atualizarSaldo(contaOrigem);
                    boolean sucessoAtualizacaoContaDestino = contaBancariaDAO.atualizarSaldo(contaDestino);

                    return sucessoTransferencia && sucessoAtualizacaoContaOrigem && sucessoAtualizacaoContaDestino;
                }
            }
        }

        return false;
    }
}

