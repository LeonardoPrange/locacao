package alura.locacao.domain.repositories;

import alura.locacao.domain.entities.Automovel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutomovelRepository extends CrudRepository<Automovel, UUID> { }
