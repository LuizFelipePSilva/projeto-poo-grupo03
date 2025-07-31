package br.com.grupo03.projetopoo.model.entity;

import br.com.grupo03.projetopoo.model.entity.enums.TipoUsuario;
import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario implements IUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    /**
     * Construtor vazio.
     * OBRIGATÓRIO para que o JPA/Hibernate funcione.
     */
    public Usuario() {
    }

    /**
     * Construtor com parâmetros para facilitar a criação de novos usuários no código.
     */
    public Usuario(String nome, String login, String senha, TipoUsuario tipo) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters...
    @Override
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    @Override
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    @Override
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    @Override
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    @Override
    public TipoUsuario getTipo() { return tipo; }
    public void setTipo(TipoUsuario tipo) { this.tipo = tipo; }
}