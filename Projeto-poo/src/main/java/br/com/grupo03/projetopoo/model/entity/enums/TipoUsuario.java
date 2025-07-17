package br.com.grupo03.projetopoo.model.entity.enums;

public enum TipoUsuario {
    GERENTE("Gerente", 1),
    FUNCIONARIO("Funcionário", 2);

    private final String descricao;
    private final int nivelAcesso;

    TipoUsuario(String descricao, int nivelAcesso) {
        this.descricao = descricao;
        this.nivelAcesso = nivelAcesso;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }
}
