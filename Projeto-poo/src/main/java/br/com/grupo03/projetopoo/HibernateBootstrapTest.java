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

            Tipo tipo = new Tipo(null, "Comida", FormaVenda.UNIDADE);
            em.persist(tipo);

            Produto produto = new Produto(null, "MarcaTeste", "123456789", 10.0, tipo);
            em.persist(produto);

            Usuario usuario = new Usuario(null, "Admin", "admin", "senha123", TipoUsuario.GERENTE);
            em.persist(usuario);

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
