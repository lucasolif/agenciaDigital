package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.exception.CotaReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.repository.ReportagemRepository;


@Service
public class ReportagemServices {

	@Autowired
	private ReportagemRepository reportagemRepository;
	
	public Reportagem salvar(Reportagem reportagem) {
	    try {
	        if (reportagem.getId() != null && !reportagem.getId().isEmpty()) {
	            return reportagemRepository.save(reportagem);
	        } else {
	            List<String> listaIdAssunto = reportagem.getAssuntos()
	                .stream()
	                .map(Assunto::getId)
	                .toList(); // ou Collectors.toList() se necessário

	            String jornalistaId = reportagem.getJornalista().getId();
	            LocalDate dataCadastro = LocalDate.now();

	            long qtdReportagem = reportagemRepository.countByJornalistaAndDataCadastroAndAssuntos(jornalistaId, dataCadastro, listaIdAssunto);
	            
	            if (qtdReportagem < 2) {
	                reportagem.setId(null);
	                reportagem.setDataCadastro(dataCadastro);
	                return reportagemRepository.save(reportagem);
	            } else {
	                throw new CotaReportagemException("Você possui " + qtdReportagem +" reportagem(ns) com os assuntos escolhidos cadastradas hoje.");
	            }
	        }
	    } catch (Exception e) {
	        throw new ErroBancoException("Erro ao salvar no banco de dados.");
	    }
	}

    public Optional<Reportagem> buscarPorId(String id) {
        return reportagemRepository.findById(id);
    }

    public List<Reportagem> listar() {
        return reportagemRepository.findAll();
    }

    public void excluir(String id) {
    	reportagemRepository.deleteById(id);
    }
    
    /*public List<ReportagemDTOConsulta> filtraReportagem(String jornalistaId, String status, String assuntoId) {      
    	
        List<Reportagem> listaReportagem = reportagemRepository.findByJornalistaIdOrStatusOrAssuntosIdsIn(jornalistaId, status, assuntoId);
        
        List<ReportagemDTOConsulta> listaReportagemDTO = listaReportagem.stream().map(reportagem -> {
            ReportagemDTOConsulta dto = new ReportagemDTOConsulta();
            dto.setTitulo(reportagem.getTitulo());
            dto.setStatus(reportagem.getStatus());
            dto.setJornalista(reportagem.getJornalista()); // <-- ESSA LINHA

            List<String> assuntosIds = reportagem.getAssuntos();
            List<Assunto> assuntos = assuntoRepository.findAllById(assuntosIds);

            dto.setAssuntos(assuntos);
            String assuntosConcat = assuntos.stream()
                .map(Assunto::getNome)
                .collect(Collectors.joining(" | "));
            dto.setAssuntosString(assuntosConcat);

            return dto;
        }).collect(Collectors.toList());
        

        return listaReportagemDTO;
    }*/

}
