package br.com.grupo03.projetopoo.model.dao;

import br.com.grupo03.projetopoo.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class UsuarioDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoPOO");

    public Usuario save(Usuario user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            if(user.getId() == null) em.persist(user);
            else em.merge(user);
            em.getTransaction().commit();
            return user;
        }
        finally {
            em.close();
        }
    }
    public Usuario findById(Long id){
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        }
        finally {
            em.close();
        }
    }

    public Usuario findByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login", Usuario.class);
            query.setParameter("login", login);
            List<Usuario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public Usuario findByName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nome = :nome", Usuario.class);
            query.setParameter("nome", name);
            List<Usuario> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    public void remove(Usuario user) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario u = em.find(Usuario.class, user.getId());
            if(u != null) em.remove(u);
            em.getTransaction().commit();
        }
        finally {
            em.close();
        }
    }

    public List<Usuario> findAllUser(){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("FROM Usuario", Usuario.class);
            return query.getResultList();
        }
        finally {
        em.close();
        }
    }
}
