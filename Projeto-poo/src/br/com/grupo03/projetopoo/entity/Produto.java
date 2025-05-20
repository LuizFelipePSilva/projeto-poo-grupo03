package br.com.grupo03.projetopoo.entity;

public class Produto {
    private final Long id;
    private String marca;
    private String codigoBarras;
    private int quantidade;
    private double preco;
    private Tipo tipo;

    public Produto(Long id, String marca, String codigoBarras, double preco, Tipo tipo){
        this.id = id;
        this.marca = marca;
        this.codigoBarras = codigoBarras;
        this.preco = preco;
        this.tipo = tipo;
        this.quantidade = 0;
    }

    public Long getId() {
        return id;
    }
    public String getMarca(){
        return marca;
    }
    public String getCodigoBarras(){
        return codigoBarras;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public double getPreco(){
        return preco;
    }
    public Tipo getTipo(){
        return tipo;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("Marca não pode ser vazia");
        }
        this.marca = marca;
    }

    public void setCodigoBarras(String codigoBarras) {
        if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
            throw new IllegalArgumentException("Código de barras não pode ser vazio");
        }
        this.codigoBarras = codigoBarras;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    public void setPreco(double preco) {
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        this.preco = preco;
    }

    public void setTipo(Tipo tipo) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo não pode ser nulo");
        }
        this.tipo = tipo;
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantidade += quantidade;
    }

    public void removerEstoque(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        if (this.quantidade < quantidade) {
            throw new IllegalStateException("Quantidade em estoque insuficiente");
        }
        this.quantidade -= quantidade;
    }
}
