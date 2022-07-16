package alura.locacao;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AutomovelTests {
    @Test
    void deveInstanciarNovoAutomovel() throws Exception {
        Automovel automovel = new Automovel("marca", "modelo", Grupo.B, 100.00, 1);
        assertNotNull(automovel.getId());
        assertEquals(automovel.getMarca(), "marca");
        assertEquals(automovel.getModelo(), "modelo");
        assertEquals(automovel.getGrupo(), Grupo.B);
        assertEquals(automovel.getValorDiaria(), 100.00);
        assertEquals(automovel.getQuantidade(), 1);
    }
}
