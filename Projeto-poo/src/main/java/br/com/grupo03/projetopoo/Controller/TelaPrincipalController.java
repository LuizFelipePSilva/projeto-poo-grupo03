package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.views.TelaLogin;

public class TelaPrincipalController {
    public void sair(){
        TelaLogin.telaLogin();
    }
    public void paginaInicial(){
        TelaLogin.telaPrincipal();
    }
    public void paginaAdmin(){
        TelaLogin.admin();
    }
}
