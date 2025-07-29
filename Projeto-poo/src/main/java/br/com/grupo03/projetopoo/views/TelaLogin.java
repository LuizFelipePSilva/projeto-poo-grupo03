package br.com.grupo03.projetopoo.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TelaLogin extends Application
{
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        telaLogin();
    }

    public static void telaLogin(){
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoo/views/TelaLogin.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Tela de Login");
        stage.setScene(scene);
        stage.show();
    }
    public static void telaPrincipal() {
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoo/views/TelaPrincipal.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Tela inicial");
        stage.setScene(scene);
        stage.show();
    }
    public static void admin(){
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoo/views/Admin.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 800, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Tela de Admin");
        stage.setScene(scene);
        stage.show();
    }

    public static void buscarProdutos() {
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoo/views/BuscarProdutos.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1185, 668);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Buscar Produtos");
        stage.setScene(scene);
        stage.show();
    }

    public static void carrinho() {
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoo/views/Carrinho.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Carrinho de Compras");
        stage.setScene(scene);
        stage.show();
    }

    public static void notaFiscal() {
        FXMLLoader fxmlLoader = new FXMLLoader(TelaLogin.class.getResource("/br/com/grupo03/projetopoe/views/NotaFiscal.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1280, 720);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.setTitle("Nota Fiscal");
        stage.setScene(scene);
        stage.show();
    }

}
