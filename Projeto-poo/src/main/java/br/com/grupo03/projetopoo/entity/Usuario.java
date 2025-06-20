package br.com.grupo03.projetopoo.entity;

import br.com.grupo03.projetopoo.entity.enums.TipoUsuario;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario", uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipo;

    public Usuario() {}

    public Usuario(Long id, String nome, String login, String senha, TipoUsuario tipo) {
        this.id = id;
        setNome(nome);
        setLogin(login);
        setSenha(senha);
        setTipo(tipo);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) throw new IllegalArgumentException("Login não pode ser vazio");
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) throw new IllegalArgumentException("Senha não pode ser vazia");
        this.senha = senha;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuario tipo) {
        if (tipo == null) throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        this.tipo = tipo;
    }
}
