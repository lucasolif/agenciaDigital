package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;
import br.edu.utfpr.td.tsi.agencia.digital.repository.JornalistaRepository;


@Service
public class JornalistaServices {

	@Autowired
	private JornalistaRepository jornalistaRepository;

    public Jornalista salvar(Jornalista jornalista) {
    	try {		
    		return jornalistaRepository.save(jornalista);	
    	} catch (DuplicateKeyException e) {
    		
            if (e.getMessage().contains("cpf")) {
                throw new DadosDuplicadosException("Já existe um jornalista com esse CPF.", e);
            } else if (e.getMessage().contains("email")) {
                throw new DadosDuplicadosException("Esse e-mail já está em uso.", e);
            } else {
                throw new DadosDuplicadosException("Dados duplicados.", e);
            }
            
        } catch (MongoException e) {
            throw new ErroBancoException("Erro ao salvar no banco", e);
        }   
    }

    public Optional<Jornalista> buscarPorId(String id) {
        return jornalistaRepository.findById(id);
    }
    
    
    public List<Jornalista> buscarNome(String nome) {
        return jornalistaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Jornalista> listar() {
        return jornalistaRepository.findAll();
    }

    public void excluir(String id) {
    	jornalistaRepository.deleteById(id);
    }

    public Jornalista atualizar(Jornalista jornalista) {
        return jornalistaRepository.save(jornalista);
    }
}
