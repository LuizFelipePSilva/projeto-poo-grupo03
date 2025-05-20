package br.com.grupo03.projetopoo.entity;

import br.com.grupo03.projetopoo.entity.enums.FormaVenda;

public class Tipo {
    private Long id;
    private String nome;
    private FormaVenda formaVenda;

    public Tipo(Long id, String nome, FormaVenda formaVenda) {
        this.id = id;
        this.nome = nome;
        this.formaVenda = formaVenda;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public FormaVenda getFormaVenda() {
        return formaVenda;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        this.nome = nome;
    }

    public void setFormaVenda(FormaVenda formaVenda) {
        if (formaVenda == null) {
            throw new IllegalArgumentException("Forma de venda não pode ser nula");
        }
        this.formaVenda = formaVenda;
    }
}
