package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public interface ReportagemRepository extends MongoRepository<Reportagem, String>, ReportagemRepositoryCustom {
	
    boolean existsByJornalistaId(String jornalistaId);
    
    boolean existsByAssuntosId(String assuntoId);
    
    Optional<Reportagem> findById(String id);
}
