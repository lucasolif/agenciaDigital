package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.dto.ReportagemDto;
import br.edu.utfpr.td.tsi.agencia.digital.exception.CotaReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.StatusReportagemException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.repository.ReportagemRepository;


@Service
public class ReportagemServices {

	@Autowired
	private ReportagemRepository reportagemRepository;
	
	public Reportagem salvar(Reportagem reportagem) {

        if (reportagem.getId() != null && !reportagem.getId().isEmpty()) {
            return reportagemRepository.save(reportagem);
        } else {
            List<String> listaIdAssunto = reportagem.getAssuntos()
                .stream()
                .map(Assunto::getId)
                .toList(); 

            String jornalistaId = reportagem.getJornalista().getId();
            LocalDate dataCadastro = LocalDate.now();

            long qtdReportagem = reportagemRepository.countByJornalistaAndDataCadastroAndAssuntos(jornalistaId, dataCadastro, listaIdAssunto);
            
            if (qtdReportagem < 2) {
                reportagem.setId(UUID.randomUUID().toString());
                reportagem.setDataCadastro(dataCadastro);
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
        return reportagemRepository.consultar(jornalistaId, assuntoId, status);
    }

}
