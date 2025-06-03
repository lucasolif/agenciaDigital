package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class SenhaIncorretaException extends RuntimeException {
    public SenhaIncorretaException(String mensagem) {
        super(mensagem);
    }
}