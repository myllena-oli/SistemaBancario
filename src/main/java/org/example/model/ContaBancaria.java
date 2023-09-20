package org.example.model;
import java.math.BigDecimal;

public class ContaBancaria {

    private int id;
    private String numeroConta;
    private BigDecimal saldo;
    private int clienteId;
    public ContaBancaria(int id, String numeroConta, BigDecimal saldo, int clienteId) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.clienteId = clienteId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

}
