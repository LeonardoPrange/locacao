package alura.locacao;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("automovel")
public class AutomovelController {
    @GetMapping
    public ArrayList<AutomovelViewModel> obtemAutomoveis() {
        ArrayList<AutomovelViewModel> automoveis = new ArrayList<AutomovelViewModel>();
        AutomovelViewModel automovel1 = new AutomovelViewModel();
        automovel1.id = UUID.randomUUID();
        automovel1.marca = "Nissan";
        automovel1.modelo = "March";
        automovel1.grupo = Grupo.B;
        automovel1.valorDiaria = 98.00;
        automovel1.quantidade = 3;
        automoveis.add(automovel1);
        return automoveis;
    }
}
