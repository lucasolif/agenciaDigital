package br.edu.utfpr.td.tsi.agencia.digital.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	
	Optional<Usuario> findByUsername(String username);
    List<Usuario> findByNomeOrUsernameContainingIgnoreCase(String nome, String username);
}
