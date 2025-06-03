package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.time.LocalDate;
import java.util.List;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDto;

public interface ReportagemRepositoryCustom {
    
	long countByJornalistaAndDataCadastroAndAssuntos(String jornalistaId, LocalDate dataCadastro, List<String> assuntoIds);
    
    List<ReportagemDto> consultar(String jornalistaId, String assuntoId, String status);
}