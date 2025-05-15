package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;

public interface JornalistaRepository extends MongoRepository<Jornalista, String> {
    
	//@Query("{ 'nome': { $regex: ?0, $options: 'i' } }")
    List<Jornalista> findByNomeContainingIgnoreCase(String nome);
	
}
