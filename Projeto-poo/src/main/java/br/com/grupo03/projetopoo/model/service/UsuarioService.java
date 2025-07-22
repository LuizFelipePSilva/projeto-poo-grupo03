package br.com.grupo03.projetopoo.model.service;

import br.com.grupo03.projetopoo.model.dao.UsuarioDAO;
import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import br.com.grupo03.projetopoo.model.entity.Usuario;
import br.com.grupo03.projetopoo.model.entity.enums.TipoUsuario;
import br.com.grupo03.projetopoo.model.service.interfaces.IUsuarioService;
import br.com.grupo03.projetopoo.model.dao.Session;

import java.util.List;
import java.util.Objects;

public class UsuarioService implements IUsuarioService {
    private final UsuarioDAO dao = new UsuarioDAO();

    private void checkGerente() {
        IUsuario logged = Session.getInstance().getLoggedUser();
        if (logged == null || logged.getTipo() != TipoUsuario.GERENTE) {
            throw new SecurityException("Apenas gerentes podem executar esta operação");
        }
    }
    public IUsuario autenticar(String login, String senha) {
        IUsuario existing = dao.findByLogin(login);
        if (existing == null || !existing.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Credenciais inválidas");
        }
        return existing;
    }

    public IUsuario saveUser(IUsuario user) {
        checkGerente();
        IUsuario existing = dao.findByLogin(user.getLogin());
        if (existing != null && !Objects.equals(existing.getId(), user.getId())) {
            throw new IllegalStateException("Login já utilizado");
        }
        return dao.save(user);
    }

    public IUsuario findById(Long id) {
        IUsuario user = dao.findById(id);
        if (user == null) throw new IllegalArgumentException("Usuário não encontrado");
        return user;
    }

    public IUsuario findByLogin(String login) {
        IUsuario user = dao.findByLogin(login);
        if (user == null) throw new IllegalArgumentException("Login não encontrado");
        return user;
    }

    public void deleteUser(Long id) {
        checkGerente();
        IUsuario user = dao.findById(id);
        if (user == null) throw new IllegalArgumentException("Usuário não encontrado");
        dao.remove(user);
    }

    public List<IUsuario> listAllUsers() {
        return dao.findAllUser();
    }
}
