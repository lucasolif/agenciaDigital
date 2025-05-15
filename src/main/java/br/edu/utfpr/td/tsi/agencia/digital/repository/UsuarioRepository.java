package br.edu.utfpr.td.tsi.agencia.digital.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;


public interface UsuarioRepository extends MongoRepository<Usuario, String>{
	Optional<Usuario> findByNome(String nome);
	Optional<Usuario> findByUsername(String username);
	
    //List<Usuario> findByNomeContainingIgnoreCase(String nome);
    
    @Query("{ '$or': [ { 'nome': { $regex: ?0, $options: 'i' } }, { 'username': { $regex: ?0, $options: 'i' } } ] }")
    List<Usuario> findByNomeOrUsernameIgnoreCase(String nome, String username);
}
