package br.edu.utfpr.td.tsi.agencia.digital.exception;

public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String mensagem) {
        super(mensagem);
    }
}