package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public interface ReportagemRepository extends MongoRepository<Reportagem, String> {
	
	List<Reportagem> findByAssuntosContainingIgnoreCase(String filtro);
	
}
