package br.com.grupo03.projetopoo.Controller.cells;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.util.CartManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;

public class QuantityCell extends TableCell<ItemNota, Void> {
    private final HBox pane = new HBox();
    private final Button minusButton = new Button("-");
    private final Button plusButton = new Button("+");
    private final Label quantityLabel = new Label();
    private ItemNota currentItem;
    private final CartManager cartManager = CartManager.getInstance();

    public QuantityCell() {
        super();
        minusButton.getStyleClass().add("table-action-button");
        plusButton.getStyleClass().add("table-action-button");

        pane.setSpacing(5);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(minusButton, quantityLabel, plusButton);

        minusButton.setOnAction(event -> {
            int newQuantity = currentItem.getQuantidade() - 1;
            if (newQuantity <= 0) {
                cartManager.removeItem(currentItem);
            } else {
                currentItem.setQuantidade(newQuantity);
                getTableView().getItems().set(getIndex(), currentItem);
            }
        });

        plusButton.setOnAction(event -> {
            int newQuantity = currentItem.getQuantidade() + 1;
            currentItem.setQuantidade(newQuantity);
            getTableView().getItems().set(getIndex(), currentItem);
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            currentItem = getTableView().getItems().get(getIndex());
            quantityLabel.setText(String.valueOf(currentItem.getQuantidade()));
            setGraphic(pane);
        }
    }
}