package br.com.grupo03.projetopoo.model.service;

import br.com.grupo03.projetopoo.entity.ItemNota;
import br.com.grupo03.projetopoo.entity.Produto;
import br.com.grupo03.projetopoo.entity.Venda;
import br.com.grupo03.projetopoo.model.dao.VendaDAO;
import br.com.grupo03.projetopoo.model.dao.interfaces.VendaInterface;
import br.com.grupo03.projetopoo.model.service.interfaces.IVendaService;
import br.com.grupo03.projetopoo.model.service.interfaces.ProdutoInterfaceService;
import java.util.Map;

public class VendaService implements IVendaService {

    private final VendaInterface vendaDAO;
    private final ProdutoInterfaceService produtoService;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.produtoService = new ProdutoService();
    }

    @Override
    public Venda performSale(Map<Long, Integer> shoppingCart) throws Exception {
        if (shoppingCart == null || shoppingCart.isEmpty()) {
            throw new IllegalArgumentException("O carrinho de compras não pode estar vazio.");
        }

        Venda newSale = new Venda();

        for (Map.Entry<Long, Integer> item : shoppingCart.entrySet()) {
            Long productId = item.getKey();
            int quantity = item.getValue();

            Produto product = produtoService.getById(productId);
            if (product == null) {
                throw new Exception("Produto com ID " + productId + " não existe.");
            }

            product.removerEstoque(quantity);

            produtoService.updateProduto(product);

            ItemNota newItemNota = new ItemNota(product, quantity);

            newSale.adicionarItem(newItemNota);
        }

        vendaDAO.save(newSale);
        return newSale;
    }
}