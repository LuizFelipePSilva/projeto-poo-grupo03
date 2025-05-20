package br.com.grupo03.projetopoo.entity;

public class Produto {
    private Long id;
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

    public double getPreco(){
        return preco;
    }

}
