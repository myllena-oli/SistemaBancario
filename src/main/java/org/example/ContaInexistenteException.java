package org.example;

public class ContaInexistenteException extends Exception {
    public ContaInexistenteException(String mensagem) {
        super(mensagem);
    }
}
