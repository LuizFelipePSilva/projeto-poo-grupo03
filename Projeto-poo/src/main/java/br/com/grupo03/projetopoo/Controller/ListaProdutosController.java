package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.views.TelaLogin;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

public class ListaProdutosController {

    @FXML
    private TextField campoBusca;

    @FXML
    private FlowPane flowProdutos;

    private List<Produto> listaProdutos;

    @FXML
    public void initialize() {
        carregarProdutos();

        campoBusca.textProperty().addListener((obs, oldVal, newVal) -> {
            filtrarProdutos(newVal);
        });
    }

    private void carregarProdutos() {
        ProdutoDAO dao = new ProdutoDAO();
        listaProdutos = dao.findAll();
        mostrarProdutos(listaProdutos);
    }

    private void mostrarProdutos(List<Produto> produtos) {
        flowProdutos.getChildren().clear();

        for (Produto p : produtos) {
            VBox card = criarCardProduto(p);
            flowProdutos.getChildren().add(card);
        }
    }

    private VBox criarCardProduto(Produto produto) {
        VBox card = new VBox(8);
        card.setPrefSize(180, 140);
        card.getStyleClass().add("produto-card");
        card.setStyle("-fx-border-color: #3a3d5c; -fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 15;");

        Label nome = new Label(produto.getMarca() != null ? produto.getMarca() : "Nome do produto");
        nome.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-text-fill: #2a2a2a;");
        Label preco = new Label(String.format("R$ %.2f", produto.getPreco()));
        preco.setStyle("-fx-font-size: 12; -fx-text-fill: #555555;");

        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER_LEFT);

        Button btnAlterar = new Button("Alterar Produtos");
        btnAlterar.getStyleClass().add("btn-alterar");
        btnAlterar.setOnAction(e -> alterarProduto(produto));

        Button btnExcluir = new Button("Excluir Produtos");
        btnExcluir.getStyleClass().add("btn-excluir");
        btnExcluir.setOnAction(e -> excluirProduto(produto));

        botoes.getChildren().addAll(btnAlterar, btnExcluir);

        card.getChildren().addAll(nome, preco, botoes);

        return card;
    }

    private void filtrarProdutos(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            mostrarProdutos(listaProdutos);
        } else {
            String filtroLower = filtro.toLowerCase();
            List<Produto> filtrados = listaProdutos.stream()
                    .filter(p -> p.getMarca() != null && p.getMarca().toLowerCase().contains(filtroLower))
                    .collect(Collectors.toList());
            mostrarProdutos(filtrados);
        }
    }

    // Métodos para ações dos botões - implemente conforme sua lógica

    public void adicionarProduto() {
        System.out.println("Botão Adicionar produtos clicado");
        // Abrir tela para adicionar produto ou modal
    }

    public void alterarProduto(Produto produto) {
        System.out.println("Alterar produto: " + produto.getMarca());
        // Abrir modal/ tela para editar o produto
    }

    public void excluirProduto(Produto produto) {
        System.out.println("Excluir produto: " + produto.getMarca());
        // Confirmar exclusão e remover produto
    }

    // Navegação (exemplo)

    public void voltarTela() {
        TelaLogin.telaPrincipal();
    }

    public void paginaInicial() {
        TelaLogin.telaPrincipal();
    }

    public void goToCarrinho() {
        TelaLogin.carrinho();
    }

    public void goToNotaFiscal() {
        TelaLogin.notaFiscal();
    }

    public void paginaAdmin() {
        TelaLogin.admin();
    }

    public void sair() {
        TelaLogin.telaLogin();
    }
}
