package br.com.grupo03.projetopoo.model.service;

import br.com.grupo03.projetopoo.model.entity.ItemNota;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.model.dao.VendaDAO;
import br.com.grupo03.projetopoo.model.dao.interfaces.VendaInterface;
import br.com.grupo03.projetopoo.model.service.interfaces.IVendaService;
import br.com.grupo03.projetopoo.model.service.interfaces.ProdutoInterfaceService;
import br.com.grupo03.projetopoo.model.service.strategy.DiscountStrategy;
import br.com.grupo03.projetopoo.model.service.strategy.NoDiscountStrategy;

import java.util.Map;

public class VendaService implements IVendaService {
    private final VendaInterface vendaDAO;
    private final ProdutoInterfaceService produtoService;

    public VendaService() {
        this.vendaDAO = new VendaDAO();
        this.produtoService = new ProdutoService();
    }

    @Override
    public Venda performSale(Map<Long, Integer> shoppingCart, DiscountStrategy discountStrategy) throws Exception {
        if (shoppingCart == null || shoppingCart.isEmpty()) {
            throw new IllegalArgumentException("O carrinho de compras não pode estar vazio.");
        }

        Venda newSale = new Venda();

        // Loop para processar cada item do carrinho
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

        // --- APLICAÇÃO DO PADRÃO STRATEGY ---
        // 1. Pega o valor total sem descontos, já calculado na entidade Venda.
        double totalWithoutDiscount = newSale.getValorTotal();

        // 2. Usa a ESTRATÉGIA fornecida para calcular o valor do desconto.
        double discountValue = discountStrategy.calculateDiscount(totalWithoutDiscount);

        // 3. Define o valor final da venda, já com o desconto aplicado.
        newSale.setValorTotal(totalWithoutDiscount - discountValue);

        // 4. Salva a venda no banco de dados com seu valor final.
        vendaDAO.save(newSale);
        return newSale;
    }

    @Override
    public Venda performSale(Map<Long, Integer> shoppingCart) throws Exception {
        return performSale(shoppingCart, new NoDiscountStrategy());
    }
}