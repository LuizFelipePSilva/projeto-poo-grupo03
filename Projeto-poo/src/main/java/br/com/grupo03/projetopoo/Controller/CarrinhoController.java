package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.Controller.cells.QuantityCell;
import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.model.service.strategy.DiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.FixedDiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.NoDiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.PercentageDiscountStrategy;
import br.com.grupo03.projetopoo.util.CartManager;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import java.io.IOException;
import java.util.Objects;

public class CarrinhoController {

    @FXML private TableView<ItemNota> tabelaCarrinho;
    @FXML private TableColumn<ItemNota, String> colunaCodigo;
    @FXML private TableColumn<ItemNota, String> colunaMarca;
    @FXML private TableColumn<ItemNota, Double> colunaPreco;
    @FXML private TableColumn<ItemNota, Void> colunaQuantidade;
    @FXML private TextField textFieldCupom;
    @FXML private Label labelValorTotal;

    private final CartManager cartManager = CartManager.getInstance();
    private DiscountStrategy activeDiscountStrategy;

    @FXML
    public void initialize() {
        this.activeDiscountStrategy = new NoDiscountStrategy();

        colunaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getCodigoBarras()));
        colunaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colunaPreco.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValorUnitario()));
        colunaQuantidade.setCellFactory(param -> new QuantityCell());


        tabelaCarrinho.setItems(cartManager.getCartItems());

        double totalWidth = 1;
        colunaCodigo.prefWidthProperty().bind(tabelaCarrinho.widthProperty().multiply(totalWidth * 0.20)); // 20%
        colunaMarca.prefWidthProperty().bind(tabelaCarrinho.widthProperty().multiply(totalWidth * 0.40));  // 40%
        colunaPreco.prefWidthProperty().bind(tabelaCarrinho.widthProperty().multiply(totalWidth * 0.15));   // 15%
        colunaQuantidade.prefWidthProperty().bind(tabelaCarrinho.widthProperty().multiply(totalWidth * 0.25)); // 25%

        tabelaCarrinho.setItems(cartManager.getCartItems());

        cartManager.getCartItems().addListener((ListChangeListener<ItemNota>) c -> updateTotalValue());
        updateTotalValue();
    }


    private void updateTotalValue() {
        double subtotal = cartManager.getCartItems().stream().mapToDouble(ItemNota::getValorTotal).sum();
        double discount = activeDiscountStrategy.calculateDiscount(subtotal);
        double finalTotal = subtotal - discount;
        labelValorTotal.setText(String.format("Total: R$ %.2f", finalTotal));
    }

    @FXML
    protected void onAplicarCupomClick() {
        String cupomCode = textFieldCupom.getText().trim().toUpperCase();
        switch (cupomCode) {
            case "DESCONTO10":
                this.activeDiscountStrategy = new PercentageDiscountStrategy(10.0);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Cupom de 10% de desconto aplicado!");
                break;
            case "20REAIS":
                this.activeDiscountStrategy = new FixedDiscountStrategy(20.0);
                showAlert(Alert.AlertType.INFORMATION, "Sucesso", "Cupom de R$ 20,00 de desconto aplicado!");
                break;
            default:
                this.activeDiscountStrategy = new NoDiscountStrategy();
                showAlert(Alert.AlertType.WARNING, "Cupom Inválido", "O código do cupom inserido não é válido.");
                break;
        }
        updateTotalValue();
    }

    @FXML
    protected void irParaNotaFiscal() {
        if (cartManager.getCartItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Carrinho Vazio", "Seu carrinho está vazio.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/br/com/grupo03/projetopoo/views/NotaFiscal.fxml")));
            Parent notaFiscalView = loader.load();
            NotaFiscalController controller = loader.getController();
            controller.iniciarDados(cartManager.getCartItems(), activeDiscountStrategy);
            BorderPane borderPane = (BorderPane) tabelaCarrinho.getScene().getRoot();
            borderPane.setCenter(notaFiscalView);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de nota fiscal.");
        }
    }

    public void sair(){ TelaLogin.telaLogin(); }
    public void paginaInicial(){ TelaLogin.telaPrincipal(); }
    public void paginaAdmin(){ TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal(){ TelaLogin.notaFiscal(); }
    public void goToProdutos() {TelaLogin.buscarProdutos();}
    public void abrirControleEstoque() { TelaLogin.controleEstoque(); }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}