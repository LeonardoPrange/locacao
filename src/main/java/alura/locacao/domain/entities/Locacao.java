package alura.locacao.domain.entities;
import alura.locacao.domain.entities.Automovel;

import javax.persistence.*;

import java.util.UUID;

@Entity(name="locacoes")
public class Locacao {
    @Id
    private UUID Id;
    private UUID automovelId;
    private UUID usuarioId;
    private double valorTotal;
    private int diarias;

    public Locacao(Automovel automovel, UUID usuarioId, int diarias) {
        double valorTotal = automovel.getValorDiaria() * diarias;
        this.Id = UUID.randomUUID();
        this.automovelId = automovel.getId();
        this.usuarioId = usuarioId;
        this.diarias = diarias;
        this.valorTotal = valorTotal;
    }

    public Locacao() {

    }
}
