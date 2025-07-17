package br.com.grupo03.projetopoo.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String marca;

    @Column(name = "codigo_barras", nullable = false, unique = true)
    private String codigoBarras;

    @Column(nullable = false)
    private int quantidade;

    @Column(nullable = false)
    private double preco;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_id", nullable = false)
    private Tipo tipo;

    public Produto() {}

    public Produto(Long id, String marca, String codigoBarras, double preco, Tipo tipo) {
        this.id = id;
        setMarca(marca);
        setCodigoBarras(codigoBarras);
        setPreco(preco);
        setTipo(tipo);
        this.quantidade = 0;
    }

    public Long getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) throw new IllegalArgumentException("Marca não pode ser vazia");
        this.marca = marca;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) throw new IllegalArgumentException("Código de barras não pode ser vazio");
        this.codigoBarras = codigoBarras;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) throw new IllegalArgumentException("Quantidade não pode ser negativa");
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco <= 0) throw new IllegalArgumentException("Preço deve ser maior que zero");
        this.preco = preco;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == null) throw new IllegalArgumentException("Tipo não pode ser nulo");
        this.tipo = tipo;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        this.quantidade += quantidade;
    }

    public void removerEstoque(int quantidade) {
        if (quantidade <= 0) throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        if (this.quantidade < quantidade) throw new IllegalStateException("Estoque insuficiente");
        this.quantidade -= quantidade;
    }

    public void atualizarProduto(String novaMarca, String novoCodigoBarras, double novoPreco, Tipo novoTipo, int novaQuantidade) {
        setMarca(novaMarca);
        setCodigoBarras(novoCodigoBarras);
        setPreco(novoPreco);
        setTipo(novoTipo);
        setQuantidade(novaQuantidade);
    }
}
