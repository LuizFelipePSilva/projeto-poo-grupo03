package br.com.grupo03.projetopoo.util;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CartManager {
    private static CartManager instance;
    private final ObservableList<ItemNota> cartItems = FXCollections.observableArrayList();

    private CartManager() {}

    public static CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addItem(ItemNota item) {
        cartItems.add(item);
    }

    public void removeItem(ItemNota item) {
        cartItems.remove(item);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public ObservableList<ItemNota> getCartItems() {
        return cartItems;
    }
}