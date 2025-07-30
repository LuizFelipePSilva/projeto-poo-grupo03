package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.Controller.cells.NotaFiscalQuantityCell;
import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.model.service.VendaService;
import br.com.grupo03.projetopoo.model.service.strategy.DiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.NoDiscountStrategy;
import br.com.grupo03.projetopoo.util.CartManager;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.util.HashMap;
import java.util.Map;

public class NotaFiscalController {

    // --- Componentes FXML ---
    @FXML private Label labelSubtotal;
    @FXML private Label labelDesconto;
    @FXML private Label labelValorTotalCompra;
    @FXML private TableView<ItemNota> tabelaItens;
    @FXML private TableColumn<ItemNota, String> colunaItemNome;
    @FXML private TableColumn<ItemNota, Void> colunaItemQtd;
    @FXML private TableColumn<ItemNota, Double> colunaItemValorUnit;
    @FXML private TableColumn<ItemNota, Double> colunaItemValorTotal;

    // --- Lógica de Negócio ---
    private final VendaService vendaService = new VendaService();
    private final CartManager cartManager = CartManager.getInstance();
    private ObservableList<ItemNota> itensDoPedido;
    private DiscountStrategy activeDiscountStrategy;

    @FXML
    public void initialize() {
        this.activeDiscountStrategy = new NoDiscountStrategy();

        colunaItemNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colunaItemValorUnit.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValorUnitario()));
        colunaItemValorTotal.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getValorTotal()));
        colunaItemQtd.setCellFactory(param -> new NotaFiscalQuantityCell(this));

        configureColumnWidths();

    }

    public void iniciarDados(ObservableList<ItemNota> itensDoCarrinho, DiscountStrategy discountStrategy) {
        this.itensDoPedido = itensDoCarrinho;
        this.activeDiscountStrategy = discountStrategy;
        tabelaItens.setItems(this.itensDoPedido);

        this.itensDoPedido.addListener((ListChangeListener<ItemNota>) c -> updateTotalValue());
        updateTotalValue();
    }

    private void updateTotalValue() {
        double subtotal = this.itensDoPedido.stream().mapToDouble(ItemNota::getValorTotal).sum();
        double discount = activeDiscountStrategy.calculateDiscount(subtotal);
        double finalTotal = subtotal - discount;

        labelSubtotal.setText(String.format("R$ %.2f", subtotal));
        labelDesconto.setText(String.format("- R$ %.2f", discount));
        labelValorTotalCompra.setText(String.format("R$ %.2f", finalTotal));
    }

    private void configureColumnWidths() {
        double totalWidth = 0.99;
        colunaItemNome.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(totalWidth * 0.45)); // 45%
        colunaItemQtd.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(totalWidth * 0.25));  // 15%
        colunaItemValorUnit.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(totalWidth * 0.15));   // 15%
        colunaItemValorTotal.prefWidthProperty().bind(tabelaItens.widthProperty().multiply(totalWidth * 0.15)); // 15%
    }

    @FXML
    protected void finalizarPedido() {
        if (itensDoPedido.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Pedido Vazio", "Não é possível finalizar um pedido vazio.");
            return;
        }
        Map<Long, Integer> shoppingCartMap = new HashMap<>();
        for (ItemNota item : itensDoPedido) {
            shoppingCartMap.put(item.getProduto().getId(), item.getQuantidade());
        }
        try {
            Venda vendaRealizada = vendaService.performSale(shoppingCartMap, activeDiscountStrategy);
            showAlert(Alert.AlertType.INFORMATION, "Pedido Finalizado", "Sua compra foi realizada com sucesso! ID da Venda: " + vendaRealizada.getId());
            cartManager.clearCart();
            TelaLogin.telaPrincipal();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro no Pedido", "Houve um erro ao finalizar o pedido: " + e.getMessage());
        }
    }


    @FXML
    public void voltarParaCarrinho() {
        TelaLogin.carrinho();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML private void goToPaginaInicial() { TelaLogin.telaPrincipal(); }
    @FXML private void goToEstoque() { System.out.println("Navegando para Estoque..."); /* TelaLogin.estoque(); */ }
    @FXML private void goToCarrinho() { TelaLogin.carrinho(); }
    @FXML private void goToProdutos() { TelaLogin.buscarProdutos(); }
    @FXML private void goToNotaFiscal() { TelaLogin.notaFiscal(); }
    @FXML private void goToAdmin() { TelaLogin.admin(); }
    @FXML private void sair() { TelaLogin.telaLogin(); }
}