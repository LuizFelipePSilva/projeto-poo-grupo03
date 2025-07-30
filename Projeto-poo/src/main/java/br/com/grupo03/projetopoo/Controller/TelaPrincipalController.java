package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;


public class TelaPrincipalController implements Initializable {
    @FXML
    private Button btnAdmin;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        boolean isGerente;
        try {
            UsuarioService.checkGerente(); // Tenta executar a verificação de gerente
            isGerente = true;
        } catch (SecurityException e) {
            isGerente = false; // Se der erro de segurança, não é gerente
        }

        // Define a visibilidade dos componentes com base na permissão
        btnAdmin.setVisible(isGerente);
        btnAdmin.setManaged(isGerente); // Garante que o botão não ocupe espaço se estiver invisível

        boxAdmin.setVisible(isGerente);
        boxAdmin.setManaged(isGerente); // Garante que o VBox não ocupe espaço se estiver invisível
    }

    @FXML
    private VBox boxAdmin;
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
