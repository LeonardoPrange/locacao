package alura.locacao.domain.entities;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "perfis")
public class Perfil implements GrantedAuthority {
    @Id
    private String nome;

    private Perfil(String nome) {
        this.nome = nome;
    }

    public Perfil() {}

    @Override
    public String getAuthority() {
        return nome;
    }

    public static Perfil usuario() { return new Perfil("USUARIO"); }
    public static Perfil admin() { return new Perfil("ADMIN"); }
}
