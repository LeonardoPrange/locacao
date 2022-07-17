package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    AutomovelRepository automovelRepository;
    LocacaoRepository locacaoRepository;
    LocacaoService locacaoService;
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
        Automovel novoAutomovel = new Automovel(
            automovelPayload.marca,
            automovelPayload.modelo,
            automovelPayload.grupo,
            automovelPayload.valorDiaria,
            automovelPayload.quantidade
        );
        automovelRepository.save(novoAutomovel);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("aluga")
    public ResponseEntity alugaAutomovel(@RequestBody AlugaAutomovelPayload alugaAutomovelPayload) throws Exception {
        locacaoService.alugaAutomovel(alugaAutomovelPayload.automovelId, alugaAutomovelPayload.usuarioId, alugaAutomovelPayload.diarias);
        return new ResponseEntity(HttpStatus.OK);
    }
}
