package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.Usuario;
import br.com.grupo03.projetopoo.model.entity.enums.TipoUsuario;
import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TelaCriarUsuarioController implements Initializable {

    @FXML
    private TextField nomeField; // Campo adicionado

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private ComboBox<TipoUsuario> tipoUsuarioComboBox;

    @FXML
    private Button btnAdmin;

    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipoUsuarioComboBox.getItems().setAll(TipoUsuario.values());

        boolean isGerente;
        try {
            UsuarioService.checkGerente();
            isGerente = true;
        } catch (SecurityException e) {
            isGerente = false;
        }

        btnAdmin.setVisible(isGerente);
        btnAdmin.setManaged(isGerente);
    }

    @FXML
    private void criarUsuario() {
        String nome = nomeField.getText(); // Valor obtido
        String login = loginField.getText();
        String senha = senhaField.getText();
        TipoUsuario tipo = tipoUsuarioComboBox.getValue();

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty() || tipo == null) {
            showAlert(Alert.AlertType.ERROR, "Erro de Validação", "Todos os campos são obrigatórios.");
            return;
        }

        try {
            // Usando a interface IUsuario, mas instanciando a classe concreta Usuario
            IUsuario novoUsuario = new Usuario(nome, login, senha, tipo);

            usuarioService.saveUser(novoUsuario);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Usuário criado com sucesso!");
            limparCampos();

        } catch (SecurityException e) {
            showAlert(Alert.AlertType.ERROR, "Acesso Negado", e.getMessage());
        } catch (IllegalStateException e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao Criar Usuário", e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro Inesperado", "Ocorreu um erro ao criar o usuário: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        TelaLogin.admin();
    }

    private void limparCampos() {
        nomeField.clear(); // Limpa o novo campo
        loginField.clear();
        senhaField.clear();
        tipoUsuarioComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void sair() { TelaLogin.telaLogin(); }
    public void paginaInicial() { TelaLogin.telaPrincipal(); }
    public void paginaAdmin() { TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal() { TelaLogin.notaFiscal(); }
    public void abrirControleEstoque() { TelaLogin.controleEstoque(); }
    public void goToProdutos() { TelaLogin.buscarProdutos(); }
}