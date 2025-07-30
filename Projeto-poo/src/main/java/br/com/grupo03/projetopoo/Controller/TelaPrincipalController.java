package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;

import java.awt.event.ActionEvent;


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
    public void abrirTelaProduto() { TelaLogin.buscarProdutos();}
    public void goToCarrinho() {TelaLogin.carrinho();}
    public void goToNotaFiscal() {TelaLogin.notaFiscal();}
    public void abrirControleEstoque() {TelaLogin.controleEstoque();}

}
