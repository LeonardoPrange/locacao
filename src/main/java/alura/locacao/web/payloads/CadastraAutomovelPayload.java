package alura.locacao.web.payloads;

import alura.locacao.domain.enums.Grupo;

public class CadastraAutomovelPayload {
    public String marca;
    public String modelo;
    public Grupo grupo;
    public Double valorDiaria;
    public int quantidade;
}
