package br.edu.utfpr.td.tsi.agencia.digital.exception;

import com.mongodb.MongoException;

@SuppressWarnings("serial")
public class ErroBancoException extends MongoException{
    public ErroBancoException(String mensagem) {
        super(mensagem);
    }

}
