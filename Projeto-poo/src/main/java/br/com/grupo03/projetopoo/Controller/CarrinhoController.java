package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.model.service.VendaService;
import br.com.grupo03.projetopoo.model.service.strategy.DiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.FixedDiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.NoDiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.PercentageDiscountStrategy;
import br.com.grupo03.projetopoo.util.CartManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CarrinhoController {

    @FXML private TableView<ItemNota> tabelaCarrinho;
    @FXML private TableColumn<ItemNota, String> colunaCodigo;
    @FXML private TableColumn<ItemNota, String> colunaNome;
    @FXML private TableColumn<ItemNota, String> colunaMarca;
    @FXML private TableColumn<ItemNota, Double> colunaPreco;
    @FXML private TableColumn<ItemNota, Integer> colunaQuantidade;
    @FXML private TextField textFieldCupom;

    private final VendaService vendaService = new VendaService();
    private final CartManager cartManager = CartManager.getInstance();
    private DiscountStrategy activeDiscountStrategy;

    @FXML
    public void initialize() {
        this.activeDiscountStrategy = new NoDiscountStrategy();
        colunaCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getCodigoBarras()));
        colunaNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca())); //ver isso aqui depois
        colunaMarca.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tabelaCarrinho.setItems(cartManager.getCartItems());
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
    }

    @FXML
    protected void finalizarCompra() {
        if (cartManager.getCartItems().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Carrinho Vazio", "Seu carrinho está vazio.");
            return;
        }
        Map<Long, Integer> shoppingCartMap = new HashMap<>();
        for (ItemNota item : cartManager.getCartItems()) {
            shoppingCartMap.put(item.getProduto().getId(), item.getQuantidade());
        }
        try {
            Venda vendaRealizada = vendaService.performSale(shoppingCartMap, activeDiscountStrategy);
            showAlert(Alert.AlertType.INFORMATION, "Compra Finalizada", "Sua compra foi realizada com sucesso!");
            cartManager.clearCart();
            abrirTelaNotaFiscal(vendaRealizada);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erro na Compra", "Houve um erro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    protected void voltar() {
        System.out.println("Botão Voltar clicado.");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void abrirTelaNotaFiscal(Venda venda) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/grupo03/projetopoo/views/NotaFiscal.fxml"));
            Parent root = loader.load();
            NotaFiscalController controller = loader.getController();
            controller.carregarDadosVenda(venda);
            Stage stage = new Stage();
            stage.setTitle("Nota Fiscal - Venda #" + venda.getId());
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível abrir a tela de nota fiscal.");
        }
    }
}