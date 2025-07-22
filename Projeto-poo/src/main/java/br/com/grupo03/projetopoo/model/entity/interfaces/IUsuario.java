package br.com.grupo03.projetopoo.model.entity.interfaces;

import br.com.grupo03.projetopoo.model.entity.enums.TipoUsuario;

public interface IUsuario {
    Long getId();
    String getNome();
    String getLogin();
    String getSenha();
    TipoUsuario getTipo();
}