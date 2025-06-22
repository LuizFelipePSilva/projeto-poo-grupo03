package br.com.grupo03.projetopoo.model.service.interfaces;

import br.com.grupo03.projetopoo.entity.Usuario;
import java.util.List;

public interface IUsuarioService {
    Usuario saveUser(Usuario user);
    Usuario findById(Long id);
    Usuario findByLogin(String login);
    void deleteUser(Long id);
    List<Usuario> listAllUsers();
}