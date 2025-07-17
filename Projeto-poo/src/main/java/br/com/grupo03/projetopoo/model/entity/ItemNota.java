package br.com.grupo03.projetopoo.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "item_nota")
public class ItemNota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private int quantidade;

    @Column(name = "valor_unitario", nullable = false)
    private double valorUnitario;

    @Column(name = "valor_total", nullable = false)
    private double valorTotal;

    public ItemNota() {}

    public ItemNota(Produto produto, int quantidade) {
        setProduto(produto);
        setQuantidade(quantidade);
        calcularTotal();
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto == null) throw new IllegalArgumentException("Produto não pode ser nulo");
        this.produto = produto;
        this.valorUnitario = produto.getPreco();
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.quantidade = quantidade;
        calcularTotal();
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void calcularTotal() {
        this.valorTotal = this.quantidade * this.valorUnitario;
    }
}
