package br.edu.utfpr.td.tsi.agencia.digital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.exception.UsuarioInativoException;
import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.repository.UsuarioRepository;


@Service
public class CustomUserDetailsServices implements UserDetailsService {

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (!Boolean.TRUE.equals(usuario.isStatus())) {
            throw new UsuarioInativoException("Usuário inativo.");
        }
        
        return org.springframework.security.core.userdetails.User.builder()
    		// Contrução do obejto User, com os dados do usuário autenticado
            .username(usuario.getUsername())
            .password(usuario.getPassword()) // já criptografada com BCrypt
            .roles("USER")
            .build();
    }
}
