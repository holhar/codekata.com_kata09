package de.holhar.kata09.domain;

/**
 * @author hharms
 */
public class PricingRule {

    private String sku;
    private int amount;
    private double price;

    // External Spring Boot configuration needs it for object binding
    public PricingRule() {
    }

    public PricingRule(String sku, int amount, double price) {
        this.sku = sku;
        this.amount = amount;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
