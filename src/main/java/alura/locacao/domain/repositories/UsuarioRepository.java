package alura.locacao.domain.repositories;

import alura.locacao.domain.entities.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {
}
