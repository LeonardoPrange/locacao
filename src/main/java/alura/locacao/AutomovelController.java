package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    private List<Automovel> automoveis = new ArrayList<Automovel>();
    @GetMapping
    public ResponseEntity<ArrayList<AutomovelViewModel>> obtemAutomoveis() {
        List<AutomovelViewModel> automoveisViewModel = this.automoveis.stream().map(_automovel -> {
            AutomovelViewModel automovel = new AutomovelViewModel();
            automovel.id = _automovel.getId();
            automovel.marca = _automovel.getMarca();
            automovel.modelo = _automovel.getModelo();
            automovel.grupo = _automovel.getGrupo();
            automovel.valorDiaria = _automovel.getValorDiaria();
            automovel.quantidade = _automovel.getQuantidade();
            return automovel;
        }).collect(Collectors.toList());
        ArrayList<AutomovelViewModel> automoveis = new ArrayList<AutomovelViewModel>(automoveisViewModel);
        return new ResponseEntity(automoveis, HttpStatus.OK);
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
        this.automoveis.add(novoAutomovel);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
