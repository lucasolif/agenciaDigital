package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;

public interface AssuntoRepository extends MongoRepository<Assunto, String> {
	
	List<Assunto>findAllByNomeContainingIgnoreCase(String nome);
		
}
