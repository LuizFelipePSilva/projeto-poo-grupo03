package br.com.grupo03.projetopoo.entity.enums;

public enum FormaVenda {
    QUILO("Venda por quilo"),
    UNIDADE("Venda por unidade");

    private final String descricao;

    FormaVenda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
