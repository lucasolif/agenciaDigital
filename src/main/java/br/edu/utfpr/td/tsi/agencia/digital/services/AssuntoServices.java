package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Assunto;
import br.edu.utfpr.td.tsi.agencia.digital.repository.AssuntoRepository;

@Service
public class AssuntoServices {
	@Autowired
	private AssuntoRepository assuntoRepository;

    public Assunto salvar(Assunto assunto) {
    	try {		
    		return assuntoRepository.save(assunto);	
    	} catch (MongoException e) {
            throw new ErroBancoException("Erro ao salvar no banco", e);
        }   
    }

    public Optional<Assunto> buscarPorId(String id) {
        return assuntoRepository.findById(id);
    }

    public List<Assunto> listarTodos() {
        return assuntoRepository.findAll();
    }
    
    public List<Assunto> buscarNome(String filtro) {
        return assuntoRepository.findAllByNomeIgnoreCase(filtro);
    }

    public void excluir(String id) {
    	assuntoRepository.deleteById(id);
    }
}
