package br.com.grupo03.projetopoo;

import br.com.grupo03.projetopoo.model.entity.*;
import br.com.grupo03.projetopoo.model.entity.enums.*;
import jakarta.persistence.*;

public class HibernateBootstrapTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projetoPOO");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Usuario usuario = new Usuario(null, "Admin", "admin", "senha123", TipoUsuario.GERENTE);
            em.persist(usuario);
            Usuario usua = new Usuario(null, "Admin2", "admin2", "senha123", TipoUsuario.FUNCIONARIO);
            em.persist(usua);

            em.getTransaction().commit();

            System.out.println("Hibernate funcionando: dados persistidos com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
