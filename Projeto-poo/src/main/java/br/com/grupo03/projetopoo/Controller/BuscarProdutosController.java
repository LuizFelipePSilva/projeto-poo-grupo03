package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.util.CartManager;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarProdutosController {

    @FXML private TextField campoBusca;
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colCodigo;
    @FXML private TableColumn<Produto, String> colMarca;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, Void> colAcao;

    private ObservableList<Produto> listaProdutos;
    private final CartManager cartManager = CartManager.getInstance();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigoBarras()));
        colMarca.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMarca()));
        colPreco.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPreco()));
        colQuantidade.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantidade()));

        Callback<TableColumn<Produto, Void>, TableCell<Produto, Void>> cellFactory = param -> {
            final TableCell<Produto, Void> cell = new TableCell<>() {
                private final Button btn = new Button("Adicionar");
                private final HBox pane = new HBox(btn);

                {
                    pane.setAlignment(Pos.CENTER);
                    btn.getStyleClass().add("table-action-button");
                    btn.setOnAction((ActionEvent event) -> {
                        Produto produto = getTableView().getItems().get(getIndex());
                        adicionarAoCarrinho(produto);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(pane);
                    }
                }
            };
            return cell;
        };

        colAcao.setCellFactory(cellFactory);
        carregarProdutos();
        campoBusca.textProperty().addListener((obs, oldVal, newVal) -> filtrarProdutos(newVal));
    }

    private void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.findAll();
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabelaProdutos.setItems(listaProdutos);
    }

    private void adicionarAoCarrinho(Produto produto) {
        if (produto.getQuantidade() > 0) {
            cartManager.addProduto(produto, 1);
            showAlert(Alert.AlertType.INFORMATION, "Sucesso",
                    "Produto '" + produto.getMarca() + "' adicionado ao carrinho!");
        } else {
            showAlert(Alert.AlertType.WARNING, "Estoque Esgotado",
                    "Este produto não está disponível em estoque.");
        }
    }

    private void filtrarProdutos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabelaProdutos.setItems(listaProdutos);
        } else {
            String filtroLower = filtro.toLowerCase();
            List<Produto> filtrados = listaProdutos.stream()
                    .filter(p -> (p.getCodigoBarras() != null && p.getCodigoBarras().toLowerCase().contains(filtroLower)) ||
                            (p.getMarca() != null && p.getMarca().toLowerCase().contains(filtroLower)))
                    .collect(Collectors.toList());
            tabelaProdutos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void sair(){ TelaLogin.telaLogin(); }
    public void paginaInicial(){ TelaLogin.telaPrincipal(); }
    public void paginaAdmin(){ TelaLogin.admin(); }
    public void voltaTelaInicial(){ TelaLogin.telaPrincipal(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal(){ TelaLogin.notaFiscal(); }
}
