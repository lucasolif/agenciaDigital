package br.edu.utfpr.td.tsi.agencia.digital.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.td.tsi.agencia.digital.model.Usuario;
import br.edu.utfpr.td.tsi.agencia.digital.repository.UsuarioRepository;


@Service
public class CustomUserDetailsServices implements UserDetailsService {

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return org.springframework.security.core.userdetails.User.builder()
    		// Contrução do obejto User, com os dados do usário autenticado
            .username(usuario.getUsername())
            .password(usuario.getPassword()) // já criptografada com BCrypt
            .roles("USER") // aqui você pode adaptar com base no Cargo ou outro campo
            .build();
    }
}
