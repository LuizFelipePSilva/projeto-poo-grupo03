package br.com.grupo03.projetopoo.model.service;

import br.com.grupo03.projetopoo.entity.Usuario;
import br.com.grupo03.projetopoo.model.dao.UsuarioDAO;

import java.util.List;
import java.util.Objects;

public class UsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    public Usuario saveUser(Usuario user) {
        Usuario existing = dao.findByLogin(user.getLogin());
        if (existing != null && !Objects.equals(existing.getId(), user.getId())) {
            throw new IllegalStateException("Login já utilizado");
        }
        return dao.save(user);
    }

    public Usuario findById(Long id) {
        Usuario user = dao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        return user;
    }

    public Usuario findByLogin(String login) {
        Usuario user = dao.findByLogin(login);
        if (user == null) {
            throw new IllegalArgumentException("Login não encontrado");
        }
        return user;
    }

    public void deleteUser(Long id) {
        Usuario user = dao.findById(id);
        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        dao.remove(user);
    }

    public List<Usuario> listAllUsers() {
        return dao.findAllUser();
    }
}
