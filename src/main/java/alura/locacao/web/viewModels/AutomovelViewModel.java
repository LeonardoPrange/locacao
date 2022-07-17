package alura.locacao.web.viewModels;

import alura.locacao.domain.enums.Grupo;

import java.util.UUID;

public class AutomovelViewModel {
    public UUID id;
    public String marca;
    public String modelo;
    public Grupo grupo;
    public Double valorDiaria;
    public int quantidade;
}
