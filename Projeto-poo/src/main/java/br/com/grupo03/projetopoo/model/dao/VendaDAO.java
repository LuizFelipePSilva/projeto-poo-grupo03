package br.com.grupo03.projetopoo.model.dao;

import br.com.grupo03.projetopoo.entity.Venda;
import br.com.grupo03.projetopoo.model.dao.interfaces.VendaInterface;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class VendaDAO implements VendaInterface {

    private final EntityManager em = JPAUtil.getEntityManagerFactory();

    @Override
    public void save(Venda venda) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(venda);
        transaction.commit();
    }

    @Override
    public Venda findById(Long id) {
        return em.find(Venda.class, id);
    }

    @Override
    public List<Venda> findAll() {
        return em.createQuery("FROM Venda", Venda.class).getResultList();
    }

    @Override
    public void update(Venda venda) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.merge(venda);
        transaction.commit();
    }

    @Override
    public void delete(Venda venda) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(venda) ? venda : em.merge(venda));
        transaction.commit();
    }
}