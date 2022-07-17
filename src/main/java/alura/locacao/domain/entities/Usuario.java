package alura.locacao.domain.entities;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "usuario")
public class Usuario {
    @Id
    private UUID id;
    private String nome;
    private String email;
    private String senha;

    public Usuario(String nome, String email, String senha) {
        this.id = UUID.randomUUID();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {}

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public boolean estaAtivo() {
        return true;
    }

    public String getRoles() {
        return null;
    }
}

