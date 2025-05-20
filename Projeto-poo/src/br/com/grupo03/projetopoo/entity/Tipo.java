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
}
