package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;

public class ControleEstoqueController {

    @FXML private TextField campoBusca;
    @FXML private TableView<Produto> tabelaEstoque;
    @FXML private TableColumn<Produto, String> colCodigo;
    @FXML private TableColumn<Produto, String> colMarca;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, String> colTipo;
    @FXML private TableColumn<Produto, Void> colAcoes;

    private ObservableList<Produto> listaProdutos;

    @FXML
    public void initialize() {
        configurarColunas();
        carregarProdutos();
        adicionarBotoesAcoes();
    }

    /** Configura as colunas da tabela */
    private void configurarColunas() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigoBarras"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colTipo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getTipo() != null ? cellData.getValue().getTipo().getNome() : ""
                )
        );
    }

    /** Busca produtos no banco e carrega na tabela */
    private void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.findAll();
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabelaEstoque.setItems(listaProdutos);
    }

    /** Adiciona botões Alterar e Excluir dentro da tabela */
    private void adicionarBotoesAcoes() {
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button btnAlterar = new Button("Alterar");
            private final Button btnExcluir = new Button("Excluir");

            {
                btnAlterar.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    abrirPopupAlterar(produto);
                });

                btnExcluir.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    confirmarExcluir(produto);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(8, btnAlterar, btnExcluir);
                    setGraphic(box);
                }
            }
        });
    }

    /** Abre a tela de adicionar produto */
    @FXML
    public void adicionarProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/br/com/grupo03/projetopoo/views/AdicionarProduto.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setTitle("Adicionar Produto");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            // Atualiza tabela depois que a tela fechar
            carregarProdutos();

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de adicionar produto:\n" + e.getMessage()).showAndWait();
        }
    }

    /** Abre modal para alterar marca, preço, quantidade e tipo */
    private void abrirPopupAlterar(Produto produto) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.setTitle("Alterar Produto");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-padding: 15;");

        TextField campoMarca = new TextField(produto.getMarca());
        TextField campoPreco = new TextField(String.valueOf(produto.getPreco()));
        TextField campoQuantidade = new TextField(String.valueOf(produto.getQuantidade()));
        TextField campoTipo = new TextField(produto.getTipo() != null ? produto.getTipo().getNome() : "");

        grid.addRow(0, new Label("Marca:"), campoMarca);
        grid.addRow(1, new Label("Preço:"), campoPreco);
        grid.addRow(2, new Label("Quantidade:"), campoQuantidade);
        grid.addRow(3, new Label("Tipo:"), campoTipo);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            try {
                produto.setMarca(campoMarca.getText());
                produto.setPreco(Double.parseDouble(campoPreco.getText()));
                produto.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
                Tipo novoTipo = new Tipo();
                novoTipo.setNome(campoTipo.getText());
                produto.setTipo(novoTipo);

                // Atualiza tabela
                tabelaEstoque.refresh();
                modal.close();

                // Aqui você pode chamar o DAO para salvar no banco
                System.out.println("Produto atualizado: " + produto.getMarca());

            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Erro ao salvar alterações: " + ex.getMessage()).showAndWait();
            }
        });

        grid.add(btnSalvar, 1, 4);

        Scene scene = new Scene(grid, 300, 250);
        modal.setScene(scene);
        modal.showAndWait();
    }

    /** Confirma e exclui um produto */
    private void confirmarExcluir(Produto produto) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Produto");
        alert.setHeaderText("Deseja excluir o produto da marca " + produto.getMarca() + "?");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                tabelaEstoque.getItems().remove(produto);
                System.out.println("Produto excluído: " + produto.getMarca());
                // Aqui você pode chamar o DAO para remover do banco
            }
        });
    }

    // Métodos de navegação
    public void sair(){ TelaLogin.telaLogin(); }
    public void paginaInicial(){ TelaLogin.telaPrincipal(); }
    public void paginaAdmin(){ TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal(){ TelaLogin.notaFiscal(); }
}
