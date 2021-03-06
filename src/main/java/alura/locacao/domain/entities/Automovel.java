package alura.locacao.domain.entities;

import alura.locacao.domain.enums.Grupo;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="automoveis")
public class Automovel {
    private static final double MENOR_DIARIA_VALIDA = 97;
    private static final int MENOR_QUANTIDADE_VALIDA = 0;
    @Id
    private UUID id;
    private String marca;
    private String modelo;
    @Enumerated(EnumType.STRING)
    private Grupo grupo;
    private Double valorDiaria;
    private int quantidade;

    public Automovel(String marca, String modelo, Grupo grupo, Double valorDiaria, int quantidade) throws Exception {
        if(valorDiaria <= MENOR_DIARIA_VALIDA) {
            throw new Exception("valor da diaria invalido");
        }
        
        if(quantidade <= MENOR_QUANTIDADE_VALIDA) {
            throw new Exception("valor da quantidade invalido");
        }
        this.id = UUID.randomUUID();
        this.marca = marca;
        this.modelo = modelo;
        this.grupo = grupo;
        this.valorDiaria = valorDiaria;
        this.quantidade = quantidade;
    }

    public Automovel() {

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

    public void locar() {
    }
}
