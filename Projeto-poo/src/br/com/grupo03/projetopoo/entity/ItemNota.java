package br.com.grupo03.projetopoo.entity;
public class ItemNota {
    private Produto produto;
    private int quantidade;
    private double valorUnitario;

    public ItemNota(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getPreco();
    }
}
