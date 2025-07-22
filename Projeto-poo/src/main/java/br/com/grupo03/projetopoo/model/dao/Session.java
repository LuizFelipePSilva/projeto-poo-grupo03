package br.com.grupo03.projetopoo.model.dao;

import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;

public class Session {
    private static final Session instance = new Session();
    private IUsuario loggedUser;

    private Session() {}

    public static Session getInstance() {
        return instance;
    }

    public void login(IUsuario user) {
        this.loggedUser = user;
    }

    public void logout() {
        this.loggedUser = null;
    }

    public IUsuario getLoggedUser() {
        return loggedUser;
    }
}
