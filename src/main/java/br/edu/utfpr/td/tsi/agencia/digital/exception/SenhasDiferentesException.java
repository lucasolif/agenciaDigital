package br.edu.utfpr.td.tsi.agencia.digital.exception;

@SuppressWarnings("serial")
public class SenhasDiferentesException extends RuntimeException {
	
    public SenhasDiferentesException(String message) {
        super(message);
    }
    
}
