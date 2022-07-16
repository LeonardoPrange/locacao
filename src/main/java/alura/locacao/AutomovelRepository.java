package alura.locacao;

import java.util.ArrayList;

public interface AutomovelRepository {
    void save(Automovel automovel) throws Exception;
    ArrayList<Automovel> list() throws Exception;
}
