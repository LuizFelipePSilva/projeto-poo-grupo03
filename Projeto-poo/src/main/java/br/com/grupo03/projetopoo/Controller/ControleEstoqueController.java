package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.service.ProdutoService;
import br.com.grupo03.projetopoo.model.service.TipoService;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.stream.Collectors;

public class ControleEstoqueController {

    @FXML private TextField campoBusca;
    @FXML private TableView<Produto> tabelaEstoque;
    @FXML private TableColumn<Produto, String> colCodigo;
    @FXML private TableColumn<Produto, String> colMarca;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colQuantidade;
    @FXML private TableColumn<Produto, String> colTipo;
    @FXML private TableColumn<Produto, Void> colAcoes;
    @FXML private Label labelMensagem;
    @FXML private Button btnAdicionar;

    private ObservableList<Produto> listaProdutos;
    private boolean isGerente = false;

    // ✅ Instância do serviço de Tipo para ser reutilizada
    private final TipoService tipoService = new TipoService();

    @FXML
    public void initialize() {
        try {
            UsuarioService.checkGerente();
            isGerente = true;
        } catch (SecurityException e) {
            isGerente = false;
        }
        btnAdicionar.setVisible(isGerente);
        colAcoes.setVisible(isGerente);

        configurarColunas();
        carregarProdutos();

        if (isGerente) {
            adicionarBotoesAcoes();
        }
        campoBusca.textProperty().addListener((obs, oldVal, newVal) -> filtrarProdutos(newVal));
    }

    /** ✅ Exibir mensagem na tela */
    private void exibirMensagem(String mensagem, boolean erro) {
        labelMensagem.setText(mensagem);
        labelMensagem.setTextFill(erro ? Color.RED : Color.GREEN);
    }

    /** Configura as colunas */
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

    /** Carrega produtos */
    private void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.findAll();
        listaProdutos = FXCollections.observableArrayList(produtos);
        tabelaEstoque.setItems(listaProdutos);
    }

    /** Filtra produtos */
    private void filtrarProdutos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabelaEstoque.setItems(listaProdutos);
        } else {
            String filtroLower = filtro.toLowerCase();
            List<Produto> filtrados = listaProdutos.stream()
                    .filter(p -> (p.getCodigoBarras() != null && p.getCodigoBarras().toLowerCase().contains(filtroLower)) ||
                            (p.getMarca() != null && p.getMarca().toLowerCase().contains(filtroLower)) ||
                            (p.getTipo() != null && p.getTipo().getNome().toLowerCase().contains(filtroLower)))
                    .collect(Collectors.toList());
            tabelaEstoque.setItems(FXCollections.observableArrayList(filtrados));
        }
    }

    /** Adiciona botões Alterar/Excluir */
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

    /** Abre tela adicionar produto */
    @FXML
    public void adicionarProduto() {
        try {
            UsuarioService.checkGerente();
            TelaLogin telaLogin = new TelaLogin();
            telaLogin.adicionarProduto();
            carregarProdutos();
            exibirMensagem("", false);
        } catch (Exception e) {
            exibirMensagem("Erro ao abrir tela de adicionar: " + e.getMessage(), true);
        }
    }

    /** Alterar produto */
    private void abrirPopupAlterar(Produto produto) {
        UsuarioService.checkGerente();
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

        // ✅ MODIFICAÇÃO: Cria uma ComboBox para os tipos
        ComboBox<String> comboTipo = new ComboBox<>();
        try {
            // Carrega os nomes dos tipos na ComboBox
            List<String> nomesDosTipos = tipoService.getAll().stream()
                    .map(Tipo::getNome)
                    .collect(Collectors.toList());
            comboTipo.setItems(FXCollections.observableArrayList(nomesDosTipos));
        } catch (Exception e) {
            exibirMensagem("Erro ao carregar tipos de produto.", true);
        }

        // Define o valor atual do produto na ComboBox
        if (produto.getTipo() != null) {
            comboTipo.setValue(produto.getTipo().getNome());
        }

        grid.addRow(0, new Label("Marca:"), campoMarca);
        grid.addRow(1, new Label("Preço:"), campoPreco);
        grid.addRow(2, new Label("Quantidade:"), campoQuantidade);
        // ✅ MODIFICAÇÃO: Adiciona a ComboBox ao invés do TextField
        grid.addRow(3, new Label("Tipo:"), comboTipo);

        Button btnSalvar = new Button("Salvar");
        btnSalvar.setOnAction(e -> {
            try {
                produto.setMarca(campoMarca.getText());
                produto.setPreco(Double.parseDouble(campoPreco.getText()));
                produto.setQuantidade(Integer.parseInt(campoQuantidade.getText()));

                // ✅ MODIFICAÇÃO: Busca o objeto Tipo com base no nome selecionado na ComboBox
                String nomeTipoSelecionado = comboTipo.getValue();
                if (nomeTipoSelecionado != null && !nomeTipoSelecionado.isEmpty()) {
                    Tipo tipoExistente = tipoService.getByName(nomeTipoSelecionado);
                    produto.setTipo(tipoExistente);
                } else {
                    // Opcional: define como nulo se nada for selecionado
                    produto.setTipo(null);
                }

                ProdutoService produtoService = new ProdutoService();
                produtoService.updateProduto(produto);

                tabelaEstoque.refresh();
                modal.close();

                exibirMensagem("Produto atualizado com sucesso!", false);
            } catch (Exception ex) {
                // Mostra a mensagem de erro na label principal
                exibirMensagem("Erro ao salvar: " + ex.getMessage(), true);
            }
        });

        grid.add(btnSalvar, 1, 4);

        Scene scene = new Scene(grid, 350, 250);
        modal.setScene(scene);
        modal.showAndWait();
    }

    /** Excluir produto */
    private void confirmarExcluir(Produto produto) {
        UsuarioService.checkGerente();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Excluir Produto");
        alert.setHeaderText("Deseja excluir o produto da marca " + produto.getMarca() + "?");

        alert.showAndWait().ifPresent(result -> {
            if (result == ButtonType.OK) {
                try {
                    ProdutoService produtoService = new ProdutoService();
                    produtoService.removeProduto(produto);

                    tabelaEstoque.getItems().remove(produto);
                    exibirMensagem("Produto removido com sucesso!", false);
                } catch (Exception e) {
                    exibirMensagem("Erro ao excluir produto: " + e.getMessage(), true);
                }
            }
        });
    }

    // Navegação
    public void sair(){ TelaLogin.telaLogin(); }
    public void paginaInicial(){ TelaLogin.telaPrincipal(); }
    public void paginaAdmin(){ TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal(){ TelaLogin.notaFiscal(); }
    public void goToProdutos() {TelaLogin.buscarProdutos(); }
    public void abrirControleEstoque() { TelaLogin.controleEstoque(); }
}