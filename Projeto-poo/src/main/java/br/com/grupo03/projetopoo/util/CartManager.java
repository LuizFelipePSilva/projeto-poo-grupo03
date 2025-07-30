package br.com.grupo03.projetopoo.util;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Gerencia o carrinho de compras em memória (Singleton)
 */
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

    /**
     * Retorna a lista de itens do carrinho (observável para TableView)
     */
    public ObservableList<ItemNota> getCartItems() {
        return cartItems;
    }

    /**
     * Adiciona um produto ao carrinho. Se já existe, aumenta a quantidade.
     */
    public void addProduto(Produto produto, int quantidade) {
        if (quantidade <= 0) return;

        for (ItemNota item : cartItems) {
            if (item.getProduto().getId().equals(produto.getId())) {
                item.setQuantidade(item.getQuantidade() + quantidade);
                return;
            }
        }

        ItemNota novoItem = new ItemNota(produto, quantidade);
        cartItems.add(novoItem);

    }

    /**
     * Remove um produto específico do carrinho.
     */
    public void removeProduto(Produto produto) {
        cartItems.removeIf(item -> item.getProduto().getId().equals(produto.getId()));
    }

    /**
     * Limpa todo o carrinho.
     */
    public void clearCart() {
        cartItems.clear();
    }
}
