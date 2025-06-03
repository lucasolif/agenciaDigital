package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class UsuarioInativoException extends RuntimeException{

	public UsuarioInativoException(String mensagem) {
        super(mensagem);
    }
}
