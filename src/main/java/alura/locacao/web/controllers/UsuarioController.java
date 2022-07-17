package alura.locacao.web.controllers;

import alura.locacao.domain.entities.Usuario;
import alura.locacao.domain.repositories.UsuarioRepository;
import alura.locacao.web.payloads.CadastraUsuarioPayload;
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
    public void cadastra(@RequestBody CadastraUsuarioPayload cadastraUsuarioPayload) {
        Usuario usuario = new Usuario(cadastraUsuarioPayload.nome, cadastraUsuarioPayload.email, cadastraUsuarioPayload.senha);
        this.usuarioRepository.save(usuario);
    }
}
