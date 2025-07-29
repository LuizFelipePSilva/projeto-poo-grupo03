package br.com.grupo03.projetopoo.model.service.strategy;

public class FixedDiscountStrategy implements DiscountStrategy {
    private final double discountValue;

    public FixedDiscountStrategy(double discountValue) {
        this.discountValue = discountValue;
    }

    @Override
    public double calculateDiscount(double saleTotal) {
        return Math.min(discountValue, saleTotal);
    }
}