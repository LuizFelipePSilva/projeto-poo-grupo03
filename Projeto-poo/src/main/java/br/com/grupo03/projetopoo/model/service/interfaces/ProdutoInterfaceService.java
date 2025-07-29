package br.com.grupo03.projetopoo.model.service.interfaces;

import br.com.grupo03.projetopoo.model.entity.Produto;

import java.util.List;

public interface ProdutoInterfaceService {
    Produto getById(Long id);
    List<Produto> getByMarca(String marca);
    Produto getByCodigoBarras(String codigoBarras);
    void registerProduto(Produto produto);
    void removeProduto(Produto produto);
    Produto updateProduto(Produto produto);
    List<Produto> getAllProdutos();
    void adicionarEstoque(Long id, int quantidade);
    void removerEstoque(Long id, int quantidade);
    void aplicarDesconto(Long id, double percentual);
    double calcularValorTotalEstoque();
    boolean disponivelEstoque(Long id);
    void criarProduto(String marca, String codigoBarras, double preco, Long tipoId);
    void criarProdutoComEstoque(String marca, String codigoBarras, double preco, Long tipoId, int quantidade);
}
