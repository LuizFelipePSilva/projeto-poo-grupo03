package br.com.grupo03.projetopoo.model.service.strategy;

public class NoDiscountStrategy implements DiscountStrategy {
    @Override
    public double calculateDiscount(double saleTotal) {
        return 0.0;
    }
}