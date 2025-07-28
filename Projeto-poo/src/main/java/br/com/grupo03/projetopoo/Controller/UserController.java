package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.model.service.interfaces.IUsuarioService;
import br.com.grupo03.projetopoo.model.dao.Session;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class UserController {

    @FXML private TextField userLogin;
    @FXML private PasswordField passwordLogin;

    private final IUsuarioService usuarioService = new UsuarioService();

    public void autenticar() {
        String login = userLogin.getText();
        String senha = passwordLogin.getText();

        try {
            IUsuario user = usuarioService.autenticar(login, senha);
            Session.getInstance().login(user);
            JOptionPane.showMessageDialog(null, "Autenticado com sucesso!");
            TelaLogin.telaPrincipal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
