package br.com.grupo03.projetopoo.model.dao.interfaces;

import br.com.grupo03.projetopoo.entity.Venda;
import java.util.List;

public interface VendaInterface {

    void save(Venda venda);
    Venda findById(Long id);
    List<Venda> findAll();
    void update(Venda venda);
    void delete(Venda venda);
}