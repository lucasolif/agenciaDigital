package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;

public interface ReportagemRepositoryCustom {
    
	long countByJornalistaAndDataCadastroAndAssuntos(String jornalistaId, LocalDate dataCadastro, List<String> assuntoIds);
    
    List<Reportagem> consultar(String jornalistaId, String assuntoId, String status);
}