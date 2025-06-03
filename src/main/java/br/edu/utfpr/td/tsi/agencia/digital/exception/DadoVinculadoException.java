package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class DadoVinculadoException extends RuntimeException {
    public DadoVinculadoException(String message) {
        super(message);
    }
}