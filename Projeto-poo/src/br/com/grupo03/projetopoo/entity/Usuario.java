package br.com.grupo03.projetopoo.entity;
import br.com.grupo03.projetopoo.entity.enums.TipoUsuario;

public class Usuario {
    private Long id;
    private String nome;
    private String login;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(Long id, String nome, String login, String senha, TipoUsuario tipo) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }
        this.login = login;
    }

    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        this.senha = senha; // Na prática, deveria hashar a senha
    }

    public void setTipo(TipoUsuario tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de usuário não pode ser nulo");
        }
        this.tipo = tipo;
    }


}
