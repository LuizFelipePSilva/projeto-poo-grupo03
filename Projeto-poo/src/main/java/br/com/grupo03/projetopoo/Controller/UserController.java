package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.Usuario;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class UserController {
    @FXML private TextField userLogin;
    @FXML private PasswordField passwordLogin;
    UsuarioService usuarioService = new UsuarioService();
    Usuario usuario = new Usuario();
    public void autenticar(){
    usuario.setLogin(userLogin.getText());
    usuario.setSenha(passwordLogin.getText());
    try {
        usuarioService.autenticar(usuario);
        JOptionPane.showMessageDialog(null, "Autenticado com sucesso!");
        TelaLogin.telaPrincipal();
    }
    catch (Exception e){
        System.out.println(e.getMessage());
    }
    }
}
