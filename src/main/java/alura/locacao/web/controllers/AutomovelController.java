package alura.locacao.web.controllers;

import alura.locacao.domain.entities.Automovel;
import alura.locacao.domain.repositories.AutomovelRepository;
import alura.locacao.domain.repositories.LocacaoRepository;
import alura.locacao.web.payloads.AlugaAutomovelPayload;
import alura.locacao.web.payloads.CadastraAutomovelPayload;
import alura.locacao.web.viewModels.AutomovelViewModel;
import alura.locacao.services.LocacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    private AutomovelRepository automovelRepository;
    private LocacaoRepository locacaoRepository;
    private LocacaoService locacaoService;
    private Logger logger = LoggerFactory.getLogger(AutomovelController.class);
    public AutomovelController(AutomovelRepository automovelRepository, LocacaoRepository locacaoRepository, LocacaoService locacaoService) {
        this.automovelRepository = automovelRepository;
        this.locacaoRepository = locacaoRepository;
        this.locacaoService = locacaoService;
    }
    @GetMapping
    public ResponseEntity<ArrayList<AutomovelViewModel>> obtemAutomoveis() throws Exception {
        ArrayList<Automovel> automoveis = (ArrayList<Automovel>) automovelRepository.findAll();
        List<AutomovelViewModel> automoveisViewModel = automoveis.stream().map(_automovel -> {
            AutomovelViewModel automovel = new AutomovelViewModel();
            automovel.id = _automovel.getId();
            automovel.marca = _automovel.getMarca();
            automovel.modelo = _automovel.getModelo();
            automovel.grupo = _automovel.getGrupo();
            automovel.valorDiaria = _automovel.getValorDiaria();
            automovel.quantidade = _automovel.getQuantidade();
            return automovel;
        }).collect(Collectors.toList());
        return new ResponseEntity(automoveisViewModel, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity cadastraAutomovel(@RequestBody CadastraAutomovelPayload automovelPayload) throws Exception {
        try {
            Automovel novoAutomovel = new Automovel(
                    automovelPayload.marca,
                    automovelPayload.modelo,
                    automovelPayload.grupo,
                    automovelPayload.valorDiaria,
                    automovelPayload.quantidade
            );
            automovelRepository.save(novoAutomovel);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception exception) {
            logger.error("Erro ao cadastrar automovel", exception.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("aluga")
    public ResponseEntity alugaAutomovel(@RequestBody AlugaAutomovelPayload alugaAutomovelPayload) throws Exception {
        locacaoService.alugaAutomovel(alugaAutomovelPayload.automovelId, alugaAutomovelPayload.usuarioId, alugaAutomovelPayload.diarias);
        return new ResponseEntity(HttpStatus.OK);
    }
}
