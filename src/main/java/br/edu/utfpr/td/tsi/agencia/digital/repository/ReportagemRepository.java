package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public interface ReportagemRepository extends MongoRepository<Reportagem, String>, ReportagemRepositoryCustom {
	
    boolean existsByJornalistaId(String jornalistaId);
    
    boolean existsByAssuntosId(String assuntoId);
    
    List<Reportagem> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String termo);
    
    Optional<Reportagem> findById(String id);
}
