package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

@Repository
public class ReportagemRepositoryImpl implements ReportagemRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long countByJornalistaAndDataCadastroAndAssuntos(String jornalistaId, LocalDate dataCadastro, List<String> assuntoIds) {
    	Query query = new Query(Criteria
    		    .where("Jornalista.$id").is(new ObjectId(jornalistaId))
    		    .and("DataCadastro").is(dataCadastro)
    		    .and("Assuntos._id").in(assuntoIds.stream().map(ObjectId::new).toList())
    		);

        return mongoTemplate.count(query, Reportagem.class);
    }
}