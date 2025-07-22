package br.com.grupo03.projetopoo.model.dao.interfaces;

import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import java.util.List;

public interface IUsuarioDAO {
    IUsuario save(IUsuario user);
    IUsuario findById(Long id);
    IUsuario findByLogin(String login);
    IUsuario findByName(String name);
    void remove(IUsuario user);
    List<IUsuario> findAllUser();
}
