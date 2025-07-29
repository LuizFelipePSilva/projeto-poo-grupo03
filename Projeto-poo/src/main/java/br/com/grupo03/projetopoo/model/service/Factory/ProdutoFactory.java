package br.com.grupo03.projetopoo.model.service.Factory;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;

public class ProdutoFactory {

    public static Produto criarProduto(String marca, String codigoBarras, double preco, Tipo tipo) {
        Produto p = new Produto();
        p.setMarca(marca);
        p.setCodigoBarras(codigoBarras);
        p.setPreco(preco);
        p.setTipo(tipo);
        p.setQuantidade(0); // regra de negócio: começa com 0 em estoque
        return p;
    }

    public static Produto criarProdutoComEstoque(String marca, String codigoBarras, double preco, Tipo tipo, int quantidade) {
        Produto p = criarProduto(marca, codigoBarras, preco, tipo);
        p.setQuantidade(quantidade);
        return p;
    }
}


