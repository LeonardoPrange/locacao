package alura.locacao.seguranca.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    public AutenticacaoService(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String login(String email, String senha) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email, senha);
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = tokenService.gerarToken(authentication);
        return token;
    }
}
