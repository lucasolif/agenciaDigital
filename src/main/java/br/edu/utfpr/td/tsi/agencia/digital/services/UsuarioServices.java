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
import br.edu.utfpr.td.tsi.agencia.digital.exception.SenhasDiferentesException;
import br.edu.utfpr.td.tsi.agencia.digital.exception.UsuarioAdministradorException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.repository.UsuarioRepository;


@Service
public class UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario salvar(UsuarioDTO usuarioDto) {
    	
    	Usuario usuario;
    	
    	try{   		
    		//Alteração do usuário
			if(usuarioDto.getId() != null && !usuarioDto.getId().isEmpty()) {
				
    			usuario = usuarioRepository.findById(usuarioDto.getId()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    		
    			usuario.setCelular(usuarioDto.getCelular());
    			usuario.setEmail(usuarioDto.getEmail());
    			usuario.setNome(usuarioDto.getNome());
    			usuario.setStatus(usuarioDto.isStatus());
    			usuario.setUsername(usuarioDto.getUsername());
	    		
	    	} else {
	    		//Novo usuário
	            usuario = new Usuario();
	            usuario.setNome(usuarioDto.getNome());
	            usuario.setCelular(usuarioDto.getCelular());
	            usuario.setEmail(usuarioDto.getEmail());
	            usuario.setUsername(usuarioDto.getUsername());
	            usuario.setStatus(usuarioDto.isStatus());

	            // Senha obrigatória no cadastro
	            if (usuarioDto.getPassword() == null || usuarioDto.getPassword().isEmpty()) {
	                throw new IllegalArgumentException("Senha é obrigatória para novo usuário.");
	            }
	            
	            // Verifica se as senhas coincidem
	            if (!usuarioDto.getPassword().equals(usuarioDto.getConfirmarSenha())) {
	                throw new SenhasDiferentesException("As senhas não coincidem.");
	            }

	            usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
	        }

	        return usuarioRepository.save(usuario);
    		
    	} catch (DadosDuplicadosException e) {		
    		throw new DadosDuplicadosException("Já existe um usuário com esse Login");    
        } catch (MongoException e) {
            throw new ErroBancoException("Erro ao salvar no banco");
        } catch(Exception e) {
        	throw new RuntimeException("Erro inespertado"+e);
		}   
        
    }

    public Optional<Usuario> buscarPorId(String id) {
        return usuarioRepository.findById(id);
    }
    
    public List<Usuario> buscarNomeUsername(String nome, String username) {
        return usuarioRepository.findByNomeOrUsernameContainingIgnoreCase(nome, username);
    }
      
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public void excluir(String id, String username) {

    	if(username.equalsIgnoreCase("admin")) {
    		throw new UsuarioAdministradorException("Não é possível excluir o usuario Admin.");
    	}
    	
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
        usuario.setPassword(dto.getPassword());
               
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
