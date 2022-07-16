package alura.locacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    private List<Automovel> automoveis = new ArrayList<Automovel>();
    @GetMapping
    public ResponseEntity<ArrayList<AutomovelViewModel>> obtemAutomoveis() {
        ArrayList<AutomovelViewModel> automoveis = new ArrayList<>();
        AutomovelViewModel automovel1 = new AutomovelViewModel();
        automovel1.id = UUID.randomUUID();
        automovel1.marca = "Nissan";
        automovel1.modelo = "March";
        automovel1.grupo = Grupo.B;
        automovel1.valorDiaria = 98.00;
        automovel1.quantidade = 3;
        automoveis.add(automovel1);
        return new ResponseEntity<>(automoveis, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity cadastraAutomovel(@RequestBody CadastraAutomovelPayload automovelPayload) {
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
