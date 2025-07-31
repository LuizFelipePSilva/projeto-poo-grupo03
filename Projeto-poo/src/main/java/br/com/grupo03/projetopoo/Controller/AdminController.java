package br.com.grupo03.projetopoo.Controller;

import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.entity.enums.FormaVenda;
import br.com.grupo03.projetopoo.model.service.TipoService;
import br.com.grupo03.projetopoo.model.service.UsuarioService;
import br.com.grupo03.projetopoo.views.TelaLogin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminController implements Initializable {

    private final TipoService tipoService = new TipoService();

    @FXML
    private TableView<Tipo> tabelaTipos;
    @FXML
    private TableColumn<Tipo, Long> colCodigoTipo;
    @FXML
    private TableColumn<Tipo, String> colNomeTipo;
    @FXML
    private TableColumn<Tipo, FormaVenda> colFormaVenda;

    @FXML
    private TextField campoNomeTipo;
    @FXML
    private ComboBox<FormaVenda> comboFormaVenda;

    @FXML
    private Button btnAdicionarTipo;
    @FXML
    private Button btnSalvarAlteracoesTipo;
    @FXML
    private Button btnExcluirTipo;

    private final ObservableList<Tipo> tiposData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboFormaVenda.setItems(FXCollections.observableArrayList(FormaVenda.values()));
        colCodigoTipo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeTipo.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colFormaVenda.setCellValueFactory(new PropertyValueFactory<>("formaVenda"));
        carregarTiposDoBanco();
        tabelaTipos.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> preencherCampos(newValue)
        );
        try {
            UsuarioService.checkGerente();
        } catch (SecurityException e) {
            // Se não for gerente, desabilita os controles de edição
            campoNomeTipo.setDisable(true);
            comboFormaVenda.setDisable(true);
            btnAdicionarTipo.setDisable(true);
            btnSalvarAlteracoesTipo.setDisable(true);
            btnExcluirTipo.setDisable(true);
            // Exibe um alerta informando o motivo
            exibirAlerta("Acesso Restrito", "Apenas usuários com perfil de GERENTE podem gerenciar os tipos de produto.");
        }
    }
    @FXML
    private void adicionarTipo() {
        try {
            UsuarioService.checkGerente();
                Tipo novoTipo = new Tipo();
                novoTipo.setNome(campoNomeTipo.getText());
                novoTipo.setFormaVenda(comboFormaVenda.getValue());

                tipoService.register(novoTipo);

                carregarTiposDoBanco(); // Atualiza a tabela com o novo item
                limparCampos();

        } catch (Exception e) {
            exibirAlerta("Erro ao Adicionar", "Falha ao cadastrar o tipo: " + e.getMessage());
        }
    }

    @FXML
    private void salvarAlteracoesTipo() {
        Tipo tipoSelecionado = tabelaTipos.getSelectionModel().getSelectedItem();
        if (tipoSelecionado == null) {
            exibirAlerta("Aviso", "Selecione um tipo na tabela para poder editar.");
            return;
        }

        try {
            UsuarioService.checkGerente();
            tipoSelecionado.setNome(campoNomeTipo.getText());
            tipoSelecionado.setFormaVenda(comboFormaVenda.getValue());
            tipoService.update(tipoSelecionado);
            System.out.println(tipoSelecionado.getFormaVenda());
            tabelaTipos.refresh();
            limparCampos();

        } catch (Exception e) {
            exibirAlerta("Erro ao Salvar", "Falha ao salvar alterações: " + e.getMessage());
        }
    }

    @FXML
    private void excluirTipo() {
        Tipo tipoSelecionado = tabelaTipos.getSelectionModel().getSelectedItem();
        if (tipoSelecionado == null) {
            exibirAlerta("Aviso", "Selecione um tipo na tabela para excluir.");
            return;
        }

        try {
            UsuarioService.checkGerente();
            tipoService.remover(tipoSelecionado.getId());
            tiposData.remove(tipoSelecionado); // Remove da lista da interface
            limparCampos();

        } catch (Exception e) {
            exibirAlerta("Erro ao Excluir", "Falha ao remover o tipo: " + e.getMessage());
        }
    }
    private void carregarTiposDoBanco() {
        try {
            tiposData.clear();
            tiposData.addAll(tipoService.getAll());
            tabelaTipos.setItems(tiposData);
        } catch (Exception e) {
            exibirAlerta("Erro de Conexão", "Não foi possível carregar os tipos do banco de dados.");
        }
    }

    private void preencherCampos(Tipo tipo) {
        if (tipo != null) {
            campoNomeTipo.setText(tipo.getNome());
            comboFormaVenda.setValue(tipo.getFormaVenda());
        }
    }

    private void limparCampos() {
        tabelaTipos.getSelectionModel().clearSelection();
        campoNomeTipo.clear();
        comboFormaVenda.setValue(null);
    }

    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    public void sair() { TelaLogin.telaLogin(); }
    public void paginaInicial() { TelaLogin.telaPrincipal(); }
    public void paginaAdmin() { TelaLogin.admin(); }
    public void goToCarrinho() { TelaLogin.carrinho(); }
    public void goToNotaFiscal() { TelaLogin.notaFiscal(); }
    public void goToProdutos() { TelaLogin.buscarProdutos(); }
    public void abrirControleEstoque() { TelaLogin.controleEstoque(); }
}