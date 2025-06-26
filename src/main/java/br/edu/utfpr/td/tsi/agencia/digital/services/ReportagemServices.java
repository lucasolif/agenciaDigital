package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDto;
import br.edu.utfpr.td.tsi.agencia.digital.exception.CotaReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.StatusReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.repository.JornalistaRepository;
import br.edu.utfpr.td.tsi.agencia.digital.repository.ReportagemRepository;



@Service
public class ReportagemServices {

	@Autowired
	private ReportagemRepository reportagemRepository;
	@Autowired
	private JornalistaRepository jornalistaRepository;
	@Autowired
	private IndexadorReportagem indexadorReportagem;
	
	
	public Reportagem salvar(Reportagem reportagem) {

		// Verifica se é alteração
        if (reportagem.getId() != null && !reportagem.getId().isEmpty()) {
            return reportagemRepository.save(reportagem);
        } else {
            List<String> listaIdAssunto = reportagem.getAssuntos().stream().map(Assunto::getId).toList(); 
            String jornalistaId = reportagem.getJornalista().getId();
            
            long qtdReportagem = reportagemRepository.countByJornalistaAndDataCadastroAndAssuntos(jornalistaId, LocalDate.now(), listaIdAssunto);

            if (qtdReportagem < 2) {
                reportagem.setId(UUID.randomUUID().toString());
                reportagem.setDataCadastro(LocalDate.now());
                return reportagemRepository.save(reportagem);
            } else {
                throw new CotaReportagemException("Você possui 2 reportagens com os assuntos escolhidos, cadastradas hoje.");
            }
        }

	}

    public Optional<Reportagem> buscarPorId(String id) {
        return reportagemRepository.findById(id);
    }
    
    public Reportagem consultarReportagemId(String id) {
    	
    	Reportagem reportagem = reportagemRepository.findById(id).orElse(null);
    	
    	if(reportagem.getStatus().equalsIgnoreCase("em produção")) {
    		return reportagem;
    	}else {
    		throw new StatusReportagemException("Só é permitido alterar reportagem com status 'Em Produção'.");
    	}
    }

    public List<Reportagem> listar() {
        return reportagemRepository.findAll();
    }

    public void excluir(String id) {
    	
    	Reportagem reportagem = reportagemRepository.findById(id).orElse(null);
    	  	
    	if(reportagem.getStatus().equalsIgnoreCase("Em produção")) {
    		reportagemRepository.deleteById(id);
    	}else {
    		throw new StatusReportagemException("Só é permitido excluir reportagem com status 'Em Produção'.");
    	}
    	
    }
    
    public List<ReportagemDto> consultarReportagens(String jornalistaId, String assuntoId, String status) {
    	
		List<ReportagemDto> listaReportagensDto = new ArrayList<ReportagemDto>();
    	List<Reportagem> listaReportagem = reportagemRepository.consultar(jornalistaId, assuntoId, status);
    	
    	for(Reportagem report : listaReportagem) {
    		Jornalista jornalista = jornalistaRepository.findJornalistaById(report.getJornalista().getId());
    		report.setJornalista(jornalista);
    		
    		ReportagemDto reportagemDto = new ReportagemDto(report);
    		listaReportagensDto.add(reportagemDto);
    	}
    	
    	return listaReportagensDto;
    }
    
	public List<ReportagemDto> listarTodosBuscaIndexada(String termo) {
		
		List<ReportagemDto> listaReportagensDto = new ArrayList<ReportagemDto>();
		List<String> ids = indexadorReportagem.procurar(termo);
		
		for (String idReportagem : ids) {	
			
			Reportagem reportagem = reportagemRepository.findById(idReportagem).orElse(null);
			Jornalista jornalista = jornalistaRepository.findJornalistaById(reportagem.getJornalista().getId());
			
			reportagem.setJornalista(jornalista);
			ReportagemDto reportagemDto = new ReportagemDto(reportagem);
			
			listaReportagensDto.add(reportagemDto);
		}	
		
		return listaReportagensDto;
	}

}
