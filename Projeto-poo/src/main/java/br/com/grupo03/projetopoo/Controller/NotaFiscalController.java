package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class NotaFiscalController {

    @FXML private Label labelNomeCliente;
    @FXML private Label labelFormaPagamento;
    @FXML private Label labelValorTotalResumo;
    @FXML private Label labelCodigoPedido;
    @FXML private Label labelTotalItens;
    @FXML private Label labelValorTotalCompra;
    @FXML private TableView<ItemNota> tabelaItens;
    @FXML private TableColumn<ItemNota, String> colunaItemNome;
    @FXML private TableColumn<ItemNota, Integer> colunaItemQtd;
    @FXML private TableColumn<ItemNota, Double> colunaItemValorUnit;
    @FXML private TableColumn<ItemNota, Double> colunaItemValorTotal;

    @FXML
    public void initialize() {
        colunaItemNome.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduto().getMarca()));
        colunaItemQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaItemValorUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        colunaItemValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
    }


    public void carregarDadosVenda(Venda venda) {
        if (venda == null) return;

        labelNomeCliente.setText("Cliente Padrão");
        labelFormaPagamento.setText("Pagamento na loja");
        labelValorTotalResumo.setText(String.format("R$ %.2f", venda.getValorTotal()));
        labelCodigoPedido.setText("CÓDIGO DO PEDIDO: " + venda.getId());
        labelTotalItens.setText("TOTAL DE ITENS: " + venda.getItens().size());
        labelValorTotalCompra.setText(String.format("VALOR TOTAL DA COMPRA: R$ %.2f", venda.getValorTotal()));
        tabelaItens.setItems(FXCollections.observableArrayList(venda.getItens()));
    }

    @FXML
    protected void finalizar() {
        Stage stage = (Stage) labelCodigoPedido.getScene().getWindow();
        stage.close();
    }

    @FXML private void goToPaginaInicial() { TelaLogin.telaPrincipal(); }
    @FXML private void goToEstoque() { System.out.println("Navegando para Estoque..."); /* TelaLogin.estoque(); */ }
    @FXML private void goToCarrinho() { TelaLogin.carrinho(); }
    @FXML private void goToProdutos() { TelaLogin.buscarProdutos(); }
    @FXML private void goToNotaFiscal() { TelaLogin.notaFiscal(); }
    @FXML private void goToAdmin() { TelaLogin.admin(); }
    @FXML private void sair() { TelaLogin.telaLogin(); }
}