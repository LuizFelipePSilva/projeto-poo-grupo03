package br.com.grupo03.projetopoo.model.service.testes;
import br.com.grupo03.projetopoo.entity.Produto;
import br.com.grupo03.projetopoo.entity.Tipo;
import br.com.grupo03.projetopoo.model.service.ProdutoService;
import br.com.grupo03.projetopoo.model.service.TipoService;

import java.util.List;


public class MainTesteProduto {
    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoService();
        TipoService tipoService = new TipoService(); // Necessário para criar um Tipo

        System.out.println("--- INICIANDO TESTES PARA ProdutoService ---");

        // --- ETAPA 1: CRIAÇÃO DE DADOS BÁSICOS (PRÉ-REQUISITO) ---
        // Para testar um Produto, primeiro precisamos de um Tipo válido no banco.
        // Supondo que você já tenha um método para registrar ou buscar um tipo.
        // Descomente e ajuste as linhas abaixo se precisar registrar um novo tipo.

        /*System.out.println("\n[1] Registrando um Tipo de pré-requisito...");
        Tipo novoTipo = new Tipo();
        novoTipo.setNome("Higiene Pessoal");
        novoTipo.setFormaVenda(FormaVenda.UNIDADE); // Ajuste o Enum conforme sua implementação
        tipoService.register(novoTipo);
        System.out.println("Tipo 'Higiene Pessoal' registrado com sucesso.");

         */

        // Vamos buscar um Tipo que já exista no banco de dados com ID 1 para o teste.
        // CERTIFIQUE-SE DE QUE UM TIPO COM ID = 1L EXISTA NO SEU BANCO.
        Tipo tipoExistente = tipoService.getByID(2L);
        if (tipoExistente == null) {
            System.out.println("ERRO: Tipo com ID 1L não encontrado. Crie um antes de rodar os testes.");
            return;
        }
        System.out.println("\n[1] Usando o tipo pré-existente: '" + tipoExistente.getNome() + "'");


        // --- ETAPA 2: TESTE DO MÉTODO registerProduto ---
        System.out.println("\n[2] Testando registerProduto...");
        Produto p1 = new Produto();
        p1.setMarca("Colgate");
        p1.setCodigoBarras("7891024131034");
        p1.setPreco(4.50);
        p1.setQuantidade(50);
        p1.setTipo(tipoExistente); // Associa o tipo ao produto

        try {
            produtoService.registerProduto(p1);
            // Após o save, o ID é atribuído pelo JPA, então já podemos usá-lo.
            System.out.println("SUCESSO: Produto '" + p1.getMarca() + "' registrado com ID: " + p1.getId());
        } catch (Exception e) {
            System.out.println("ERRO ao registrar produto: " + e.getMessage());
        }

        // --- ETAPA 3: TESTE DE EXCEÇÃO (CÓDIGO DE BARRAS DUPLICADO) ---
        System.out.println("\n[3] Testando exceção para código de barras duplicado...");
        Produto pDuplicado = new Produto();
        pDuplicado.setMarca("Outra Marca");
        pDuplicado.setCodigoBarras("7891024131034"); // Mesmo código de barras
        pDuplicado.setPreco(5.00);
        pDuplicado.setQuantidade(10);
        pDuplicado.setTipo(tipoExistente);
        try {
            produtoService.registerProduto(pDuplicado);
        } catch (RuntimeException e) {
            System.out.println("SUCESSO: Exceção capturada como esperado: " + e.getMessage());
        }


        // --- ETAPA 4: TESTE DOS MÉTODOS DE BUSCA ---
        System.out.println("\n[4] Testando métodos de busca...");
        Produto pEncontradoId = produtoService.getById(p1.getId());
        System.out.println("Busca por ID (" + p1.getId() + "): " + (pEncontradoId != null ? pEncontradoId.getMarca() : "Não encontrado"));

        Produto pEncontradoCodigo = produtoService.getByCodigoBarras("7891024131034");
        System.out.println("Busca por Código de Barras (7891024131034): " + (pEncontradoCodigo != null ? pEncontradoCodigo.getMarca() : "Não encontrado"));

        List<Produto> produtosPorMarca = produtoService.getByMarca("Colgate");
        System.out.println("Busca por Marca ('Colgate'): Encontrados " + produtosPorMarca.size() + " produto(s).");


        // --- ETAPA 5: TESTE DO MÉTODO updateProduto ---
        System.out.println("\n[5] Testando updateProduto...");
        if (pEncontradoId != null) {
            pEncontradoId.setPreco(4.99); // Alterando o preço
            pEncontradoId.setMarca("Colgate Total 12"); // Alterando a marca
            produtoService.updateProduto(pEncontradoId);

            Produto pAtualizado = produtoService.getById(p1.getId());
            System.out.println("SUCESSO: Produto atualizado. Nova marca: " + pAtualizado.getMarca() + ", Novo preço: R$" + pAtualizado.getPreco());
        }


        // --- ETAPA 6: TESTE DOS MÉTODOS DE CONTROLE DE ESTOQUE ---
        System.out.println("\n[6] Testando controle de estoque...");
        System.out.println("Estoque inicial: " + p1.getQuantidade());

        produtoService.adicionarEstoque(p1.getId(), 10);
        System.out.println("Estoque após adicionar 10 unidades: " + produtoService.getById(p1.getId()).getQuantidade());

        produtoService.removerEstoque(p1.getId(), 5);
        System.out.println("Estoque após remover 5 unidades: " + produtoService.getById(p1.getId()).getQuantidade());

        boolean disponivel = produtoService.disponivelEstoque(p1.getId());
        System.out.println("Produto está disponível em estoque? " + (disponivel ? "Sim" : "Não"));


        // --- ETAPA 7: TESTE DE MÉTODOS FINANCEIROS ---
        System.out.println("\n[7] Testando métodos financeiros...");
        System.out.println("Preço original: R$" + produtoService.getById(p1.getId()).getPreco());
        produtoService.aplicarDesconto(p1.getId(), 10.0); // 10% de desconto
        System.out.println("Preço após 10% de desconto: R$" + String.format("%.2f", produtoService.getById(p1.getId()).getPreco()));

        double valorTotalEstoque = produtoService.calcularValorTotalEstoque();
        System.out.println("Valor total do estoque (todos os produtos): R$" + String.format("%.2f", valorTotalEstoque));


        // --- ETAPA 8: TESTE DO MÉTODO removeProduto ---
        System.out.println("\n[8] Testando removeProduto...");
        try {
            produtoService.removeProduto(p1);
            Produto pRemovido = produtoService.getById(p1.getId());
            if (pRemovido == null) {
                System.out.println("SUCESSO: Produto com ID " + p1.getId() + " foi removido.");
            } else {
                System.out.println("ERRO: Falha ao remover o produto.");
            }
        } catch (Exception e) {
            System.out.println("ERRO ao remover produto: " + e.getMessage());
        }

        System.out.println("\n--- TESTES FINALIZADOS ---");
    }
}