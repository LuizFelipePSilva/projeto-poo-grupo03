package br.com.grupo03.projetopoo.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "venda_id", nullable = false)
    private List<ItemNota> itens = new ArrayList<>();

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    public Venda() {
        this.data = LocalDateTime.now();
        this.valorTotal = 0.0;
    }

    public Venda(Long id) {
        this();
        this.id = id;
    }

    // --- Getters ---

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public List<ItemNota> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public double getValorTotal() {
        return valorTotal;
    }


    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }


    public void adicionarItem(ItemNota item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        this.itens.add(item);
        recalcularValorTotal();
    }


    public void removerItem(ItemNota item) {
        if (item == null) {
            throw new IllegalArgumentException("Item não pode ser nulo");
        }
        this.itens.remove(item);
        recalcularValorTotal();
    }


    private void recalcularValorTotal() {
        this.valorTotal = this.itens.stream()
                .mapToDouble(ItemNota::getValorTotal)
                .sum();
    }
}