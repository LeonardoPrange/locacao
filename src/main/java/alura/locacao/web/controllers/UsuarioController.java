package alura.locacao.web.controllers;

import alura.locacao.domain.entities.Perfil;
import alura.locacao.domain.entities.Usuario;
import alura.locacao.domain.repositories.UsuarioRepository;
import alura.locacao.web.payloads.CadastraUsuarioPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioController {
    private UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CadastraUsuarioPayload cadastraUsuarioPayload) {
        String senhaEncriptada = new BCryptPasswordEncoder().encode(cadastraUsuarioPayload.senha);
        Perfil papel = cadastraUsuarioPayload.email.equals("admin@email.com") ? Perfil.admin() : Perfil.usuario();
        Usuario usuario = new Usuario(
                cadastraUsuarioPayload.nome,
                cadastraUsuarioPayload.email,
                senhaEncriptada,
                papel
        );
        this.usuarioRepository.save(usuario);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
