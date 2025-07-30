package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.dao.TipoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class AdicionarProdutoController {

    @FXML private TextField campoMarca;
    @FXML private TextField campoCodigoBarras;
    @FXML private TextField campoPreco;
    @FXML private TextField campoQuantidade;
    @FXML private ComboBox<Tipo> comboTipo;

    @FXML
    public void initialize() {
        carregarTipos();
    }

    private void carregarTipos() {
        try {
            TipoDAO tipoDAO = new TipoDAO();
            List<Tipo> tipos = tipoDAO.findAll(); // busca todos os tipos do banco
            comboTipo.getItems().clear();
            comboTipo.getItems().addAll(tipos);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao carregar tipos", "Não foi possível carregar os tipos do banco: " + e.getMessage());
        }
    }

    @FXML
    private void salvarProduto() {
        try {
            String marca = campoMarca.getText();
            String codigo = campoCodigoBarras.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            Tipo tipo = comboTipo.getValue();

            if (marca.isEmpty() || codigo.isEmpty() || tipo == null) {
                showAlert(Alert.AlertType.WARNING, "Campos obrigatórios", "Preencha todos os campos antes de salvar.");
                return;
            }

            Produto produto = new Produto();
            produto.setMarca(marca);
            produto.setCodigoBarras(codigo);
            produto.setPreco(preco);
            produto.setQuantidade(quantidade);
            produto.setTipo(tipo);

            ProdutoDAO dao = new ProdutoDAO();
            dao.save(produto);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso!");
            TelaLogin.telaPrincipal();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Preço e quantidade devem ser numéricos.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar produto: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        TelaLogin.telaPrincipal();
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
    public void goToProdutos() {TelaLogin.buscarProdutos();}
}
