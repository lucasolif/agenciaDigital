package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

@Repository
public class ReportagemRepositoryImpl implements ReportagemRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public long countByJornalistaAndDataCadastroAndAssuntos(String jornalistaId, LocalDate dataCadastro, List<String> assuntoIds) {
    	Query query = new Query(Criteria.where("jornalistaId").is(dataCadastro).and("Assuntos._id").in(assuntoIds));

        return mongoTemplate.count(query, Reportagem.class);
    }
     
    @Override
    public List<ReportagemDto> consultar(String jornalistaId, String assuntoId, String status) {
        Query query = new Query();
        List<Criteria> criterios = new ArrayList<>();

        if (jornalistaId != null && !jornalistaId.isBlank()) {
            criterios.add(Criteria.where("jornalista.id").is(jornalistaId));
        }

        if (assuntoId != null && !assuntoId.isBlank()) {
            criterios.add(Criteria.where("assuntos.id").is(assuntoId));
        }

        if (status != null && !status.isBlank()) {
            criterios.add(Criteria.where("status").is(status));
        }

        if (!criterios.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criterios.toArray(new Criteria[0])));
        }

        // Recebe uma lista do tipo Reportagem
        List<Reportagem> listaReportagem = mongoTemplate.find(query, Reportagem.class);

        //Mapea e converte para ReportagemDto
        return listaReportagem.stream().map(ReportagemDto::new).toList();
    }
}