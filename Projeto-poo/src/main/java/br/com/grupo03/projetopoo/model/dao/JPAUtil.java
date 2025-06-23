package br.com.grupo03.projetopoo.model.dao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoPOO");

    public static EntityManager getEntityManagerFactory() {
        return emf.createEntityManager();
    }
}