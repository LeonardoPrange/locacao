package alura.locacao.seguranca.services;

import alura.locacao.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {
    @Value("${locacao.jwt.expiration}") //Obtem variaveis do arquivo application.properties
    private String expiration;

    @Value("${locacao.jwt.secret}") //Obtem variaveis do arquivo application.properties
    private String secret;


    public String extrairToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.substring(7);
    }
    public String gerarToken(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal(); //obtem usuário
        Date dataAtual = new Date(); // define data de criação
        Date dataExpiracao = new Date(dataAtual.getTime() + Long.parseLong(expiration)); //define data de expiração
        return Jwts.builder()
                .setIssuer("locacao-api") // origem
                .setSubject(usuarioLogado.getId().toString()) // identificador
                .setIssuedAt(dataAtual) //data de criação
                .setExpiration(dataExpiracao) //data de expiração
                .signWith(SignatureAlgorithm.HS256, secret) // algoritmo de criptografia
                .compact(); //montagem do token
    }

    public UUID obterIdUsuarioDoToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return UUID.fromString(claims.getSubject());
        } catch (Exception ex) {
            return null;
        }
    }
}
