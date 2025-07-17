package br.com.grupo03.projetopoo.model.dao.interfaces;
import java.util.List;
import br.com.grupo03.projetopoo.model.entity.Tipo;
//CRUD
public interface TipoInterface {
    Tipo findById(Long id);
    Tipo findByName(String nome);
    void save(Tipo tipo);
    Tipo update(Tipo tipo);
    void delete(Tipo tipo);
    List<Tipo> findAll();
}
