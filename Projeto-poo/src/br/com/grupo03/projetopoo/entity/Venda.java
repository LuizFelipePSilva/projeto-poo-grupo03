package br.com.grupo03.projetopoo.entity;
import java.time.LocalDateTime;
import java.util.List;

public class Venda {
    private Long id;
    private LocalDateTime data;
    private List<ItemNota> itens;
    private double valorTotal;
    
}
