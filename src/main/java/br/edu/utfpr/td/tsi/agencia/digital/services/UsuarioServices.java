package br.edu.utfpr.td.tsi.agencia.digital.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import br.edu.utfpr.td.tsi.agencia.digital.dto.UsuarioDTO;
import br.edu.utfpr.td.tsi.agencia.digital.exception.DadosDuplicadosException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.ErroBancoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.repository.UsuarioRepository;


@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvar(Usuario usuario) {
    	try {
    		return usuarioRepository.save(usuario);
    	} catch (DadosDuplicadosException e) {
    		
            if (e.getMessage().contains("username")) {
                throw new DadosDuplicadosException("Já existe um usuário com esse nome de login.", e);
            } else if (e.getMessage().contains("email")) {
                throw new DadosDuplicadosException("Esse e-mail já está em uso.", e);
            } else {
                throw new DadosDuplicadosException("Dados duplicados.", e);
            }
            
        } catch (MongoException e) {
            throw new ErroBancoException("Erro ao salvar no banco", e);
        }   
        
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }
    
    public List<Usuario> buscarNome(String nome, String username) {
        return usuarioRepository.findByNomeOrUsernameIgnoreCase(nome, username);
    }
    
    public List<Usuario> buscarPorNomeOuUsername(String nomeUsername) {
        if (nomeUsername == null || nomeUsername.isBlank()) {
            return listar();
        }
        
        return usuarioRepository.findByNomeOrUsernameIgnoreCase(nomeUsername, nomeUsername);
    }
    
    public Usuario buscarPorNome(String username) {
        return usuarioRepository.findByUsername(username)
        		.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void excluir(String id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha); // Criptografa a senha
    }

    // Método para converter DTO para entidade Usuario
    public Usuario converterParaUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setCelular(dto.getCelular());
        usuario.setUsername(dto.getUsername());
        usuario.setStatus(dto.isStatus()); 
               
        //Verifica se é alteração
        if(!dto.getId().isEmpty()) {
        	usuario.setId(dto.getId());
        }

        return usuario;
    }
    
    // Método para converter Usuario para DTO
    public UsuarioDTO converterParaDTO(Usuario usuario) {
    	UsuarioDTO usuarioDTO = new UsuarioDTO();

        // Definir os dados básicos
    	usuarioDTO.setId(usuario.getId());
    	usuarioDTO.setNome(usuario.getNome());
    	usuarioDTO.setEmail(usuario.getEmail());
    	usuarioDTO.setCelular(usuario.getCelular());
    	usuarioDTO.setUsername(usuario.getUsername());  
    	usuarioDTO.setPassword(usuario.getPassword());  
    	usuarioDTO.setStatus(usuario.isStatus());

        return usuarioDTO;
    }
    
}
