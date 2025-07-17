package br.com.grupo03.projetopoo.model.service;
import br.com.grupo03.projetopoo.model.entity.Produto;
import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.dao.ProdutoDAO;
import br.com.grupo03.projetopoo.model.dao.TipoDAO;
import br.com.grupo03.projetopoo.model.service.interfaces.ProdutoInterfaceService;

import java.util.List;

public class ProdutoService implements ProdutoInterfaceService {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final TipoDAO tipoDAO = new TipoDAO();
    @Override
    public Produto getById(Long id) {
        return produtoDAO.findById(id);
    }

    @Override
    public List<Produto> getByMarca(String marca) {
        return produtoDAO.findByMarca(marca);
    }

    @Override
    public Produto getByCodigoBarras(String codigoBarras) {
        return produtoDAO.findByCodigoBarras(codigoBarras);
    }

    @Override
    public void registerProduto(Produto produto) {
        if(produtoDAO.findByCodigoBarras(produto.getCodigoBarras()) != null){
            throw new RuntimeException("Já existe um produto com este código de barras.");
        }
        produtoDAO.save(produto);
    }

    @Override
    public void removeProduto(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido para remoção.");
        }
        Produto produtoExistente = produtoDAO.findById(produto.getId());
        if (produtoExistente == null) {
            throw new RuntimeException("Produto não encontrado para remoção.");
        }
        produtoDAO.delete(produtoExistente);
    }

    @Override
    public Produto updateProduto(Produto produto) {
        if (produto == null || produto.getId() == null) {
            throw new IllegalArgumentException("Produto inválido para atualização.");
        }

        Produto produtoExistente = produtoDAO.findById(produto.getId());
        if (produtoExistente == null) {
            throw new RuntimeException("Produto não encontrado para atualização.");
        }

        produtoExistente.setMarca(produto.getMarca());
        produtoExistente.setCodigoBarras(produto.getCodigoBarras());
        produtoExistente.setPreco(produto.getPreco());
        produtoExistente.setQuantidade(produto.getQuantidade());

        if (produto.getTipo() == null || produto.getTipo().getId() == null) {
            throw new IllegalArgumentException("Tipo inválido. O produto deve ter um tipo com ID válido.");
        }

        Tipo novoTipo = tipoDAO.findById(produto.getTipo().getId());
        if (novoTipo == null) {
            throw new RuntimeException("Tipo de produto não encontrado no banco de dados.");
        }

        produtoExistente.setTipo(novoTipo);
        produtoDAO.update(produtoExistente);
        return produtoExistente;
    }


    @Override
    public List<Produto> getAllProdutos() {
        return produtoDAO.findAll();
    }

    @Override
    public void adicionarEstoque(Long id, int quantidade) {
        Produto produto = produtoDAO.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado para adicionar estoque.");
        }
        produto.adicionarEstoque(quantidade);
        produtoDAO.update(produto);
    }

    @Override
    public void removerEstoque(Long id, int quantidade) {
        Produto produto = produtoDAO.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado para remover estoque.");
        }
        produto.removerEstoque(quantidade);
        produtoDAO.update(produto);
    }

    @Override
    public void aplicarDesconto(Long id, double percentual) {
        Produto produto = produtoDAO.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado para aplicar desconto.");
        }
        if (percentual <= 0 || percentual >= 100) {
            throw new IllegalArgumentException("Percentual de desconto inválido.");
        }
        double novoPreco = produto.getPreco() * (1 - percentual / 100);
        produto.setPreco(novoPreco);
        produtoDAO.update(produto);
    }

    @Override
    public double calcularValorTotalEstoque() {
        List<Produto> produtos = produtoDAO.findAll();
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco() * produto.getQuantidade();
        }
        return total;
    }

    @Override
    public boolean disponivelEstoque(Long id) {
        Produto produto = produtoDAO.findById(id);
        if (produto == null) {
            throw new RuntimeException("Produto não encontrado.");
        }
        return produto.getQuantidade() > 0;
    }
}
