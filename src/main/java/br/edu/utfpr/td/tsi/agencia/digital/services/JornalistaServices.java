package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if(jornalista.getId() != null && jornalista.getId().isEmpty()){
			jornalista.setId(UUID.randomUUID().toString());
		}
		return jornalistaRepository.save(jornalista);	
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

    	boolean existe =  reportagemRepository.existsByJornalistaId(idJornalista);

        if (existe) {
            throw new DadoVinculadoException("Não é possível excluir o(a) jornalista. Ele(a) está vinculado(a) a uma ou mais reportagens.");
        }

        jornalistaRepository.deleteById(idJornalista);
    }

    public Jornalista atualizar(Jornalista jornalista) {
        return jornalistaRepository.save(jornalista);
    }
}
