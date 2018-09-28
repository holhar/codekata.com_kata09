package de.holhar.kata09.domain;

import java.util.Objects;

/**
 * @author hharms
 */
public class Item {

    private String sku;
    private double price;

    public Item(String sku, double price) {
        this.sku = sku;
        this.price = price;
    }

    public String getSku() {
        return sku;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(sku, item.sku);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sku);
    }

    @Override
    public String toString() {
        return "Item{" +
                "sku='" + sku + '\'' +
                ", price=" + price +
                '}';
    }
}
