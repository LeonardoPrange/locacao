package alura.locacao;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface LocacaoRepository extends CrudRepository<Locacao, UUID>  {
}
