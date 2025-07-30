package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.service.TipoService;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;

public class AdicionarProdutoController {

    @FXML private TextField campoMarca;
    @FXML private TextField campoCodigoBarras;
    @FXML private TextField campoPreco;
    @FXML private TextField campoQuantidade;
    @FXML private ComboBox<String> comboTipo; // ✅ Agora só trabalha com nomes

    private final TipoService tipoService = new TipoService();

    @FXML
    public void initialize() {
        carregarTipos();
    }

    /** ✅ Carrega apenas os nomes dos tipos no ComboBox */
    private void carregarTipos() {
        try {
            List<Tipo> tipos = tipoService.getAll();
            comboTipo.getItems().clear();
            for (Tipo t : tipos) {
                comboTipo.getItems().add(t.getNome());
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro ao carregar tipos", "Não foi possível carregar os tipos: " + e.getMessage());
        }
    }

    /** ✅ Botão + para adicionar um novo tipo apenas com o nome */
    @FXML
    private void adicionarNovoTipo() {
            try {
            TelaLogin.admin();
            } catch (RuntimeException e) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível adicionar o tipo: " + e.getMessage());
            }
    }

    /** ✅ Salvar produto pegando o nome do tipo e convertendo para entidade */
    @FXML
    private void salvarProduto() {
        try {
            UsuarioService.checkGerente();
            String marca = campoMarca.getText();
            String codigo = campoCodigoBarras.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            String nomeTipo = comboTipo.getValue();

            if (marca.isEmpty() || codigo.isEmpty() || nomeTipo == null) {
                showAlert(Alert.AlertType.WARNING, "Campos obrigatórios", "Preencha todos os campos antes de salvar.");
                return;
            }

            Tipo tipoSelecionado = tipoService.getByName(nomeTipo);
            if (tipoSelecionado == null) {
                showAlert(Alert.AlertType.ERROR, "Erro", "Tipo selecionado não existe mais no banco.");
                return;
            }

            Produto produto = new Produto();
            produto.setMarca(marca);
            produto.setCodigoBarras(codigo);
            produto.setPreco(preco);
            produto.setQuantidade(quantidade);
            produto.setTipo(tipoSelecionado);

            ProdutoDAO dao = new ProdutoDAO();
            dao.save(produto);

            showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Produto cadastrado com sucesso!");
            TelaLogin.controleEstoque();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro de Formato", "Preço e quantidade devem ser numéricos.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Erro ao salvar produto: " + e.getMessage());
        }
    }

    @FXML
    private void cancelar() {
        TelaLogin.controleEstoque();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ✅ Métodos de navegação
    public void sair() { TelaLogin.telaLogin(); }
    public void paginaInicial() { TelaLogin.telaPrincipal(); }
    public void paginaAdmin() { TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal() { TelaLogin.notaFiscal(); }
    public void abrirControleEstoque() { TelaLogin.controleEstoque(); }
    public void goToProdutos() { TelaLogin.buscarProdutos(); }
}
