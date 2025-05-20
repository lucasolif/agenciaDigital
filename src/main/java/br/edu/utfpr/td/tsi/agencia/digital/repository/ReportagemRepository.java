package br.edu.utfpr.td.tsi.agencia.digital.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public interface ReportagemRepository extends MongoRepository<Reportagem, String>, ReportagemRepositoryCustom {
	
    @Query(value = "{ 'Jornalista.$id': ?0 }", exists = true)
    boolean existsByJornalistaId(ObjectId jornalistaId);
    
    @Query(value = "{ 'Assuntos._id': ?0 }", exists = true)
    boolean existsByAssuntosId(ObjectId assuntoId);
}
