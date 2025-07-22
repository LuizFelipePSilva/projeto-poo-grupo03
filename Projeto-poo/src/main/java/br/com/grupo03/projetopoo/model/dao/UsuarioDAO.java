package br.com.grupo03.projetopoo.model.dao;

import br.com.grupo03.projetopoo.model.entity.enums.TipoUsuario;
import br.com.grupo03.projetopoo.model.dao.interfaces.IUsuarioDAO;
import br.com.grupo03.projetopoo.model.entity.Usuario;
import br.com.grupo03.projetopoo.model.entity.interfaces.IUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {

    private static EntityManager getEntityManager() {
        return JPAUtil.getEntityManagerFactory();
    }

    @Override
    public IUsuario save(IUsuario user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = (Usuario) user;
            if (u.getId() == null) em.persist(u);
            else em.merge(u);
            em.getTransaction().commit();
            return u;
        } finally {
            em.close();
        }
    }

    @Override
    public IUsuario findById(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public IUsuario findByLogin(String login) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
            query.setParameter("login", login);
            List<Usuario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public IUsuario findByName(String name) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome", Usuario.class);
            query.setParameter("nome", name);
            List<Usuario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void remove(IUsuario user) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, user.getId());
            if (u != null) em.remove(u);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public List<IUsuario> findAllUser() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("FROM Usuario", Usuario.class);
            return query.getResultList().stream().map(u -> (IUsuario) u).toList();
        } finally {
            em.close();
        }
    }
}
