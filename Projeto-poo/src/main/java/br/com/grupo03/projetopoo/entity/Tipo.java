package br.com.grupo03.projetopoo.entity;

import br.com.grupo03.projetopoo.entity.enums.FormaVenda;
import jakarta.persistence.*;

@Entity
@Table(name = "tipo")
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_venda")
    private FormaVenda formaVenda;

    public Tipo() {}

    public Tipo(Long id, String nome, FormaVenda formaVenda) {
        this.id = id;
        setNome(nome);
        setFormaVenda(formaVenda);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome;
    }

    public void setId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido. O ID deve ser um número positivo e não nulo.");
        }
        this.id = id;
    }

    public FormaVenda getFormaVenda() {
        return formaVenda;
    }

    public void setFormaVenda(FormaVenda formaVenda) {
        if (formaVenda == null) throw new IllegalArgumentException("Forma de venda não pode ser nula");
        this.formaVenda = formaVenda;
    }
}
