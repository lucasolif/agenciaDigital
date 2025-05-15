package br.edu.utfpr.td.tsi.agencia.digital.exception;

public class ErroBancoException extends RuntimeException{
    public ErroBancoException(String mensagem) {
        super(mensagem);
    }

    public ErroBancoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
