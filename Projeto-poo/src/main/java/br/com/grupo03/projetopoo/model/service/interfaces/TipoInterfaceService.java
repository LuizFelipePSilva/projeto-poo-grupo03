package br.com.grupo03.projetopoo.model.service.interfaces;
import br.com.grupo03.projetopoo.entity.Tipo;
import java.util.List;

public interface TipoInterfaceService {
    Tipo getByID(Long id);
    Tipo getByName(String name);
    void register(Tipo tipo); // na uml tem um metodo adcionarCategoria(String categoria): void
    void remover(Long id);
    Tipo updateNome(String novoNome, Long id);
    List<Tipo> getAll();
}
