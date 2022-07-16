package alura.locacao;

import java.util.UUID;

public class Automovel {
    private final UUID id;
    private final String marca;
    private final String modelo;
    private final Grupo grupo;
    private final Double valorDiaria;
    private int quantidade;

    public Automovel(String marca, String modelo, Grupo grupo, Double valorDiaria, int quantidade) {
        this.id = UUID.randomUUID();
        this.marca = marca;
        this.modelo = modelo;
        this.grupo = grupo;
        this.valorDiaria = valorDiaria;
        this.quantidade = quantidade;
    }

    public UUID getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public Double getValorDiaria() {
        return valorDiaria;
    }

    public int getQuantidade() { return quantidade; }
}
