package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class LocacaoService {
    private AutomovelRepository automovelRepository;
    private LocacaoRepository locacaoRepository;

    public LocacaoService(AutomovelRepository automovelRepository, LocacaoRepository locacaoRepository) {
        this.automovelRepository = automovelRepository;
        this.locacaoRepository = locacaoRepository;
    }

    public void alugaAutomovel(UUID automovelId, UUID usuarioId, int diarias) throws Exception {
        Optional<Automovel> automovelOptional = this.automovelRepository
                .findById(automovelId);

        if(automovelOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "automóvel não foi encontrado");

        Automovel automovel = automovelOptional.get();
        Locacao locacao = novaLocacao(usuarioId, diarias, automovel);
        concluiLocacao(automovel, locacao);
    }

    private void concluiLocacao(Automovel automovel, Locacao locacao) {
        this.locacaoRepository.save(locacao);
        this.automovelRepository.save(automovel);
    }

    private Locacao novaLocacao(UUID usuarioId, int diarias, Automovel automovel) throws Exception {
        if(automovel.getQuantidade() == 0)
            throw new Exception("automóvel nao esta disponível para locação");
        automovel.locar();
        Locacao locacao = new Locacao(automovel, usuarioId, diarias);
        return locacao;
    }
}
