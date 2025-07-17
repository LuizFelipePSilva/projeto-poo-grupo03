package br.com.grupo03.projetopoo.model.dao.interfaces;

import br.com.grupo03.projetopoo.model.entity.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    Usuario save(Usuario user);
    Usuario findById(Long id);
    Usuario findByLogin(String login);
    Usuario findByName(String name);
    void remove(Usuario user);
    List<Usuario> findAllUser();
}
