package br.edu.utfpr.td.tsi.agencia.digital.exception;

public class DadosDuplicadosException extends RuntimeException {
	
    public DadosDuplicadosException(String mensagem) {
        super(mensagem);
    }

    public DadosDuplicadosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}