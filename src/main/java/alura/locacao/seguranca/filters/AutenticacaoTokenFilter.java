package alura.locacao.seguranca.filters;

import alura.locacao.domain.entities.Usuario;
import alura.locacao.domain.repositories.UsuarioRepository;
import alura.locacao.seguranca.services.TokenService;
import alura.locacao.web.controllers.AutomovelController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;
    private Logger logger = LoggerFactory.getLogger(AutenticacaoTokenFilter.class);

    public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = tokenService.extrairToken(request);
        UUID idUsuario = tokenService.obterIdUsuarioDoToken(token);
        if(idUsuario != null) {
            autenticarUsuario(idUsuario);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(UUID idUsuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(idUsuario);
        if(optionalUsuario.isPresent()) {
            try {
                Usuario usuario = optionalUsuario.get();
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }catch (Exception ex) {
                logger.error("Erro ao autenticar usuário", ex.getMessage(), idUsuario);
            }
        } else {
            logger.warn("Usuário não encontrar ao autenticar", idUsuario);
        }
    }
}
