package br.com.grupo03.projetopoo.model.dao;
import br.com.grupo03.projetopoo.entity.Tipo;
import br.com.grupo03.projetopoo.model.dao.interfaces.TipoInterface;
import jakarta.persistence.*;
import java.util.List;


public class TipoDAO implements TipoInterface {
    private final EntityManager em = JPAUtil.getEntityManagerFactory();

    @Override
    public Tipo findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id não pode ser nulo ao buscar Tipo.");
        }
        return em.find(Tipo.class, id);
    }

    @Override
    public Tipo findByName(String nome) {
        TypedQuery<Tipo> query = em.createQuery(
                "SELECT t FROM Tipo t WHERE t.nome = :n", Tipo.class);
        query.setParameter("n", nome);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public void save(Tipo tipo) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(tipo);
        tx.commit();
    }

    @Override
    public Tipo update(Tipo tipo) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Tipo merged = em.merge(tipo);
        tx.commit();
        return merged;
    }

    @Override
    public void delete(Tipo tipo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(tipo) ? tipo : em.merge(tipo));
        transaction.commit();
    }

    @Override
    public List<Tipo> findAll() {
        return em.createQuery("FROM Tipo", Tipo.class).getResultList();
    }
}
