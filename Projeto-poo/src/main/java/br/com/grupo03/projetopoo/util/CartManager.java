package br.com.grupo03.projetopoo.util;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Optional;

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
    public ObservableList<ItemNota> getCartItems() {
        return cartItems;
    }

    public void addItem(ItemNota itemParaAdicionar) {
        Produto produtoParaAdicionar = itemParaAdicionar.getProduto();
        int quantidadeParaAdicionar = itemParaAdicionar.getQuantidade();

        Optional<ItemNota> itemExistenteOpt = cartItems.stream()
                .filter(item -> item.getProduto().getId().equals(produtoParaAdicionar.getId()))
                .findFirst();

        if (itemExistenteOpt.isPresent()) {
            ItemNota itemExistente = itemExistenteOpt.get();
            int novaQuantidade = itemExistente.getQuantidade() + quantidadeParaAdicionar;
            itemExistente.setQuantidade(novaQuantidade);

            int index = cartItems.indexOf(itemExistente);
            cartItems.set(index, itemExistente);

        } else {
            cartItems.add(itemParaAdicionar);
        }
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
