package br.com.grupo03.projetopoo.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Long id;
    private LocalDateTime data;
    private List<ItemNota> itens;
    private double valorTotal;

    public Venda(Long id){
        this.id = id;
        this.data = LocalDateTime.now();
        this.itens = new ArrayList<>();
        this.valorTotal = 0.0;
    }

    public Long getId(){
        return id;
    }
    public LocalDateTime getData(){
        return data;
    }
    public List<ItemNota> getItens(){
        return new ArrayList<>(itens);
    }
    public double getValorTotal(){
        return valorTotal;
    }
}
