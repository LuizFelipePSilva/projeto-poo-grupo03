package br.com.grupo03.projetopoo.model.service.interfaces;

import br.com.grupo03.projetopoo.model.entity.Venda;
import br.com.grupo03.projetopoo.model.service.strategy.DiscountStrategy;
import java.util.Map;

public interface IVendaService {

    Venda performSale(Map<Long, Integer> shoppingCart, DiscountStrategy discountStrategy) throws Exception;

    Venda performSale(Map<Long, Integer> shoppingCart) throws Exception;
}