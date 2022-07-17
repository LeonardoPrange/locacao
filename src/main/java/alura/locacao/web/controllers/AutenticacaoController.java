package alura.locacao.web.controllers;

import alura.locacao.seguranca.services.AutenticacaoService;
import alura.locacao.web.payloads.LoginPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("autenticacao")
public class AutenticacaoController {
    private AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginPayload payload) {
        try {
            String token = autenticacaoService.login(payload.email, payload.senha);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch(AuthenticationException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
