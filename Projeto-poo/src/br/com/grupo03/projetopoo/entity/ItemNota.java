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

    public Produto getProduto(){
        return produto;
    }
    public int getQuantidade(){
        return quantidade;
    }
    public double getValorUnitario(){
        return valorUnitario;
    }
    public double getValorTotal(){
        return quantidade * valorUnitario;
    }

    public void setQuantidade(int quantidade){
        if(quantidade <= 0){
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
            this.quantidade = quantidade;
    }
}
