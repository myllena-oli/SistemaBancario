package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {
    private int id;
    private LocalDateTime data;
    private BigDecimal valor;
    private int contaOrigemId;
    private int contaDestinoId;

    public Transacao(BigDecimal valor, int contaOrigemId, int contaDestinoId) {
        this.data = LocalDateTime.now();
        this.valor = valor;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
    }

    public Transacao(int id, LocalDateTime data, BigDecimal valor, int contaOrigemId, int contaDestinoId) {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.contaOrigemId = contaOrigemId;
        this.contaDestinoId = contaDestinoId;
    }

    public LocalDateTime getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getContaOrigemId() {
        return contaOrigemId;
    }

    public int getContaDestinoId() {
        return contaDestinoId;
    }

}
