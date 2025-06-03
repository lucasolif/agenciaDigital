package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class DadosDuplicadosException extends RuntimeException {
	
    public DadosDuplicadosException(String mensagem) {
        super(mensagem);
    }

}