package br.com.grupo03.projetopoo.model.service.testes;

import br.com.grupo03.projetopoo.model.entity.Tipo;
import br.com.grupo03.projetopoo.model.entity.enums.FormaVenda;
import br.com.grupo03.projetopoo.model.service.TipoService;

import java.util.List;

public class MainTesteTipo {
    public static void main(String[] args) {
        TipoService tipoService = new TipoService();

        System.out.println("--- INICIANDO TESTES PARA TipoService ---");

        // --- ETAPA 1: Testando o registro de um novo Tipo ---
        System.out.println("\n[1] Testando o método register...");
        Tipo tipo1 = new Tipo();
        tipo1.setNome("Bebidas");
        tipo1.setFormaVenda(FormaVenda.UNIDADE);  // Ajuste o enum conforme sua implementação

        try {
            tipoService.register(tipo1);
            System.out.println("SUCESSO: Tipo 'Bebidas' registrado com ID: " + tipo1.getId());
        } catch (RuntimeException e) {
            System.out.println("ERRO ao registrar tipo: " + e.getMessage());
        }

        // --- ETAPA 2: Testando exceção ao tentar registrar um Tipo com nome duplicado ---
        System.out.println("\n[2] Testando exceção para nome duplicado...");
        Tipo tipoDuplicado = new Tipo();
        tipoDuplicado.setNome("Bebidas");  // Mesmo nome
        tipoDuplicado.setFormaVenda(FormaVenda.UNIDADE);

        try {
            tipoService.register(tipoDuplicado);
        } catch (RuntimeException e) {
            System.out.println("SUCESSO: Exceção capturada como esperado: " + e.getMessage());
        }

        // --- ETAPA 3: Testando busca por ID ---
        System.out.println("\n[3] Testando getByID...");
        Tipo tipoBuscado = tipoService.getByID(tipo1.getId());
        if (tipoBuscado != null) {
            System.out.println("SUCESSO: Tipo encontrado: " + tipoBuscado.getNome());
        } else {
            System.out.println("ERRO: Tipo não encontrado.");
        }

        // --- ETAPA 4: Testando busca por nome ---
        System.out.println("\n[4] Testando getByName...");
        Tipo tipoPorNome = tipoService.getByName("Bebidas");
        if (tipoPorNome != null) {
            System.out.println("SUCESSO: Tipo encontrado por nome: " + tipoPorNome.getNome());
        } else {
            System.out.println("ERRO: Tipo não encontrado por nome.");
        }

        // --- ETAPA 5: Testando listagem de todos os Tipos ---
        System.out.println("\n[5] Testando getAll...");
        List<Tipo> todosTipos = tipoService.getAll();
        System.out.println("Total de tipos encontrados: " + todosTipos.size());
        for (Tipo t : todosTipos) {
            System.out.println(" - ID: " + t.getId() + ", Nome: " + t.getNome());
        }

        // --- ETAPA 6: Testando updateNome ---
        System.out.println("\n[6] Testando updateNome...");
        try {
            Tipo tipoAtualizado = tipoService.updateNome("Bebidas Não Alcoólicas", tipo1.getId());
            System.out.println("SUCESSO: Tipo atualizado. Novo nome: " + tipoAtualizado.getNome());
        } catch (RuntimeException e) {
            System.out.println("ERRO ao atualizar nome: " + e.getMessage());
        }

        // --- ETAPA 7: Testando remoção de Tipo ---
        System.out.println("\n[7] Testando remover...");
        try {
            tipoService.remover(tipo1.getId());
            Tipo tipoRemovido = tipoService.getByID(tipo1.getId());

            if (tipoRemovido == null) {
                System.out.println("SUCESSO: Tipo removido com sucesso.");
            } else {
                System.out.println("ERRO: Tipo ainda existe após tentativa de remoção.");
            }

        } catch (RuntimeException e) {
            System.out.println("ERRO ao remover tipo: " + e.getMessage());
        }

        System.out.println("\n--- TESTES FINALIZADOS PARA TipoService ---");
    }
}
