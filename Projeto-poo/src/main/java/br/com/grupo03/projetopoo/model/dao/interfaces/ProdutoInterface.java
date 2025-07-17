package br.com.grupo03.projetopoo.model.dao.interfaces;
import java.util.List;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;

public interface ProdutoInterface {
    Produto findById(Long id);
    List<Produto> findByMarca(String marca);
    Produto findByCodigoBarras(String codigoBarras);
    List<Produto> findByTipo(Tipo tipo);
    void save(Produto produto);
    Produto update(Produto produto);
    void delete(Produto produto);
    List<Produto> findAll();
}
