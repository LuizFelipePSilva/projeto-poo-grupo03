package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.util.List;
import java.util.stream.Collectors;

public class BuscarProdutosController {

    @FXML private TextField campoBusca;
    @FXML private TableView<Produto> tabelaProdutos;
    @FXML private TableColumn<Produto, String> colCodigo;
    @FXML private TableColumn<Produto, String> colMarca;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;

    private ObservableList<Produto> listaProdutos;

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigoBarras()));
        colMarca.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getMarca()));
        colPreco.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPreco()));
        colQuantidade.setCellValueFactory(data ->
                new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantidade()));

        carregarProdutos();

        campoBusca.textProperty().addListener((obs, oldVal, newVal) -> filtrarProdutos(newVal));
    }

    @FXML
    private void abrirTelaProduto(ActionEvent event) {
        TelaLogin.buscarProdutos();
    }

    private void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.findAll();
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabelaProdutos.setItems(listaProdutos);
    }

    private void filtrarProdutos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabelaProdutos.setItems(listaProdutos);
        } else {
            String filtroLower = filtro.toLowerCase();
            List<Produto> filtrados = listaProdutos.stream()
                    .filter(p ->
                            (p.getCodigoBarras() != null && p.getCodigoBarras().toLowerCase().contains(filtroLower)) ||
                                    (p.getMarca() != null && p.getMarca().toLowerCase().contains(filtroLower))
                    )
                    .collect(Collectors.toList());
            tabelaProdutos.setItems(FXCollections.observableArrayList(filtrados));
        }
    }
    public void sair(){
        TelaLogin.telaLogin();
    }
    public void paginaInicial(){
        TelaLogin.telaPrincipal();
    }
    public void paginaAdmin(){
        TelaLogin.admin();
    }
    public void voltaTelaInicial(){TelaLogin.telaPrincipal();}
    //public void abrirCarrinho { TelaLogin.Carrinho}

}
