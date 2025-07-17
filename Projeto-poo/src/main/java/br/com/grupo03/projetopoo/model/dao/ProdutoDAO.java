package br.com.grupo03.projetopoo.model.dao;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.dao.interfaces.ProdutoInterface;
import jakarta.persistence.*;
import java.util.List;

public class ProdutoDAO implements ProdutoInterface {
    private final EntityManager em = JPAUtil.getEntityManagerFactory();

    @Override
    public Produto findById(Long id) {
        return em.find(Produto.class, id);
    }

    @Override
    public List<Produto> findByMarca(String marca) {
        TypedQuery<Produto> query = em.createQuery(
                "SELECT p FROM Produto p WHERE p.marca = :m", Produto.class);
        query.setParameter("m", marca);
        return query.getResultList();
    }


    @Override
    public Produto findByCodigoBarras(String codigoBarras) {
        TypedQuery<Produto> query = em.createQuery(
                "SELECT p FROM Produto p WHERE p.codigoBarras = :c", Produto.class);
        query.setParameter("c", codigoBarras);
        return query.getResultStream().findFirst().orElse(null);
    }

    @Override
    public List<Produto> findByTipo(Tipo tipo) {
        TypedQuery<Produto> query = em.createQuery(
                "SELECT p FROM Produto p WHERE p.tipo.nome = :t", Produto.class);
        query.setParameter("t", tipo);
        return query.getResultList();
    }

    @Override
    public void save(Produto produto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(produto);
        transaction.commit();
    }

    @Override
    public Produto update(Produto produto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Produto mergedProduto = em.merge(produto);
        transaction.commit();
        return mergedProduto;
    }

    @Override
    public void delete(Produto produto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(em.contains(produto) ? produto : em.merge(produto));
        transaction.commit();
    }

    @Override
    public List<Produto> findAll() {
        return em.createQuery("FROM Produto", Produto.class).getResultList();
    }

}
