package br.com.grupo03.projetopoo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaLogin extends Application {
    public static Stage stage;

    @Override
    public void start(Stage stage) {
        TelaLogin.stage = stage;
        br.com.grupo03.projetopoo.HibernateBootstrapTest.main(new String[0]);
        telaLogin();
    }

    private static void carregarTela(String caminhoFXML, String titulo) {
        FXMLLoader loader = new FXMLLoader(TelaLogin.class.getResource(caminhoFXML));
        try {
            Scene scene = new Scene(loader.load());
            stage.setTitle(titulo);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void telaLogin() {
        carregarTela("/br/com/grupo03/projetopoo/views/TelaLogin.fxml", "Tela de Login");
    }

    public static void telaPrincipal() {
        carregarTela("/br/com/grupo03/projetopoo/views/TelaPrincipal.fxml", "Tela Inicial");
    }

    public static void admin() {
        carregarTela("/br/com/grupo03/projetopoo/views/Admin.fxml", "Tela de Admin");
    }

    public static void buscarProdutos() {
        carregarTela("/br/com/grupo03/projetopoo/views/BuscarProdutos.fxml", "Buscar Produtos");
    }

    public static void carrinho() {
        carregarTela("/br/com/grupo03/projetopoo/views/Carrinho.fxml", "Carrinho de Compras");
    }

    public static void notaFiscal() {
        carregarTela("/br/com/grupo03/projetopoo/views/NotaFiscal.fxml", "Nota Fiscal");
    }

    public static void controleEstoque() {
        carregarTela("/br/com/grupo03/projetopoo/views/ControleEstoque.fxml", "Controle de Estoque");
    }

    public void adicionarProduto() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/com/grupo03/projetopoo/views/AdicionarProduto.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            stage.setTitle("Adicionar Produto");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Erro ao abrir tela de adicionar produto:\n" + e.getMessage()).showAndWait();
        }
    }
}

