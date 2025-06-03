package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class StatusReportagemException extends RuntimeException {
    public StatusReportagemException(String mensagem) {
        super(mensagem);
    }
}
