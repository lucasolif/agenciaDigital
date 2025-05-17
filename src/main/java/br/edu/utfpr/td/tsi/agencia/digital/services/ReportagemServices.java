package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Reportagem;
import br.edu.utfpr.td.tsi.agencia.digital.repository.ReportagemRepository;

@Service
public class ReportagemServices {

	@Autowired
	private ReportagemRepository reportagemRepository;

    public Reportagem salvar(Reportagem reportagem) {
    	try {		
    		return reportagemRepository.save(reportagem);	
    	} catch (MongoException e) {
            throw new ErroBancoException("Erro ao salvar no banco");
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
}
