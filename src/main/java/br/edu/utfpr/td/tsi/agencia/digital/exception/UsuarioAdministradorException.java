package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class UsuarioAdministradorException extends RuntimeException {
    public UsuarioAdministradorException(String mensagem) {
        super(mensagem);
    }
}
