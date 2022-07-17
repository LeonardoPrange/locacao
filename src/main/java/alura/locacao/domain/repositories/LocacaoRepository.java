package alura.locacao.domain.repositories;

import alura.locacao.domain.entities.Locacao;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocacaoRepository extends CrudRepository<Locacao, UUID>  {
}
