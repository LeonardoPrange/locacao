package alura.locacao;

import org.junit.jupiter.api.Assertions;
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

    @Test
    void deveDispararExceptionQuandoValorDiariaInvalido()  {
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            new Automovel("marca", "modelo", Grupo.B, 10.00, 1);
        });
        Assertions.assertEquals("valor da diaria invalido", thrown.getMessage());
    }

    @Test
    void deveDispararExceptionQuandoQuantidadeInvalido()  {
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            new Automovel("marca", "modelo", Grupo.B, 100.00, 0);
        });
        Assertions.assertEquals("valor da quantidade invalido", thrown.getMessage());
    }
}
