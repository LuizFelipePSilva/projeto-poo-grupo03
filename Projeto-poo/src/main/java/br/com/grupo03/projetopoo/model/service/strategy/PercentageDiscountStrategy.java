package br.com.grupo03.projetopoo.model.service.strategy;

public class PercentageDiscountStrategy implements DiscountStrategy {
    private final double percentage;

    public PercentageDiscountStrategy(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double calculateDiscount(double saleTotal) {
        if (percentage > 0 && percentage <= 100) {
            return saleTotal * (percentage / 100.0);
        }
        return 0; // Nenhum desconto se a porcentagem for inválida
    }
}