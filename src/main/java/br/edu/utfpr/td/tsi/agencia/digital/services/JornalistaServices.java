package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.DadoVinculadoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Jornalista;
import br.edu.utfpr.td.tsi.agencia.digital.repository.JornalistaRepository;
import br.edu.utfpr.td.tsi.agencia.digital.repository.ReportagemRepository;


@Service
public class JornalistaServices {

	@Autowired
	private JornalistaRepository jornalistaRepository;
	@Autowired
	private ReportagemRepository reportagemRepository;

    public Jornalista salvar(Jornalista jornalista) {
    	try {		
    		if(jornalista.getId() != null && jornalista.getId().isEmpty()){
    			jornalista.setId(null);
    		}
    		return jornalistaRepository.save(jornalista);	
    	} catch (DadosDuplicadosException e) {
            throw new DadosDuplicadosException("CPF, Email ou telefone já está em uso por outro jornalista.");      
        } catch (ErroBancoException e) {
            throw new ErroBancoException("Erro ao salvar no banco");
        }   
    }

    public Optional<Jornalista> buscarPorId(String id) {
        return jornalistaRepository.findById(id);
    } 
    
    public List<Jornalista> buscarNome(String nome) {
        return jornalistaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Jornalista> listarTodos() {
        return jornalistaRepository.findAll();
    }

    public void excluir(String idJornalista) {
    	
    	ObjectId jornalistaId = new ObjectId(idJornalista);
    	boolean existe =  reportagemRepository.existsByJornalistaId(jornalistaId);
    	
        if (existe) {
            throw new DadoVinculadoException("Não é possível excluir o(a) jornalista. Ele(a) está vinculado(a) a uma ou mais reportagens.");
        }

        jornalistaRepository.deleteById(idJornalista);
    }

    public Jornalista atualizar(Jornalista jornalista) {
        return jornalistaRepository.save(jornalista);
    }
}
