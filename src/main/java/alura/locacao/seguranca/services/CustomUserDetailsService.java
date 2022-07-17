package alura.locacao.seguranca.services;

import alura.locacao.domain.entities.Usuario;
import alura.locacao.domain.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = this.usuarioRepository.findByEmail(username);
        if(usuario.isEmpty())
            throw new UsernameNotFoundException("Usuario inválido");

        return usuario.get();
    }
}
