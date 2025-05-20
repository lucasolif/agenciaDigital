package br.edu.utfpr.td.tsi.agencia.digital.repository;

import java.time.LocalDate;
import java.util.List;

public interface ReportagemRepositoryCustom {
    long countByJornalistaAndDataCadastroAndAssuntos(String jornalistaId, LocalDate dataCadastro, List<String> assuntoIds);
}