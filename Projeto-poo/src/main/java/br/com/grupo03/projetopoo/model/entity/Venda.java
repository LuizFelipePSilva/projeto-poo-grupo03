package br.com.grupo03.projetopoo.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime data;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Long getId() {
        return id;
    }

    public LocalDateTime getData() {
        return data;
    }

    public List<ItemNota> getItens() {
        return List.copyOf(itens);
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void adicionarItem(ItemNota item) {
        if (item == null) throw new IllegalArgumentException("Item não pode ser nulo");
        this.itens.add(item);
        this.valorTotal += item.getValorTotal();
    }
}
