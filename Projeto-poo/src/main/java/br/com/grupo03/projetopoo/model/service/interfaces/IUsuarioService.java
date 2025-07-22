package br.com.grupo03.projetopoo.model.service.interfaces;

import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import java.util.List;

public interface IUsuarioService {
    IUsuario autenticar(String login, String senha);
    IUsuario saveUser(IUsuario user);
    IUsuario findById(Long id);
    IUsuario findByLogin(String login);
    void deleteUser(Long id);
    List<IUsuario> listAllUsers();
}
