package br.com.grupo03.projetopoo.Controller.cells;

import br.com.grupo03.projetopoo.Controller.NotaFiscalController;
import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.util.CartManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class NotaFiscalQuantityCell extends TableCell<ItemNota, Void> {
    private final HBox pane = new HBox();
    private final Button minusButton = new Button("-");
    private final Button plusButton = new Button("+");
    private final Label quantityLabel = new Label();
    private ItemNota currentItem;
    private final CartManager cartManager = CartManager.getInstance();
    private final NotaFiscalController controller;

    public NotaFiscalQuantityCell(NotaFiscalController controller) {
        super();
        this.controller = controller;

        minusButton.getStyleClass().add("table-action-button");
        plusButton.getStyleClass().add("table-action-button");

        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(minusButton, quantityLabel, plusButton);

        minusButton.setOnAction(event -> {
            int newQuantity = currentItem.getQuantidade() - 1;
            if (newQuantity <= 0) {
                cartManager.removeItem(currentItem);
                // Se o carrinho ficar vazio, volta para a tela do carrinho
                if (cartManager.getCartItems().isEmpty()) {
                    controller.voltarParaCarrinho();
                }
            } else {
                currentItem.setQuantidade(newQuantity);
                getTableView().getItems().set(getIndex(), currentItem); // Força a atualização
            }
        });

        plusButton.setOnAction(event -> {
            int newQuantity = currentItem.getQuantidade() + 1;
            currentItem.setQuantidade(newQuantity);
            getTableView().getItems().set(getIndex(), currentItem); // Força a atualização
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            currentItem = getTableView().getItems().get(getIndex());
            quantityLabel.setText(String.format("%02d", currentItem.getQuantidade()));
            setGraphic(pane);
        }
    }
}