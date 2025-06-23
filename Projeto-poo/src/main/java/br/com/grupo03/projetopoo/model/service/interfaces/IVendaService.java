package br.com.grupo03.projetopoo.model.service.interfaces;

import br.com.grupo03.projetopoo.entity.Venda;
import java.util.Map;

public interface IVendaService {

    Venda performSale(Map<Long, Integer> shoppingCart) throws Exception;

}